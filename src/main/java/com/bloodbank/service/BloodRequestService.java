package com.bloodbank.service;

import com.bloodbank.dto.BloodRequestDto;
import com.bloodbank.entity.BloodIssue;
import com.bloodbank.entity.BloodRequest;
import com.bloodbank.entity.RequestStatus;
import com.bloodbank.entity.UrgencyLevel;
import com.bloodbank.entity.User;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.BloodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final UserService userService;
    private final BloodStockService bloodStockService;
    private final BloodIssueService bloodIssueService;

    @Autowired
    public BloodRequestService(BloodRequestRepository bloodRequestRepository,
                               UserService userService,
                               BloodStockService bloodStockService,
                               BloodIssueService bloodIssueService) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.userService = userService;
        this.bloodStockService = bloodStockService;
        this.bloodIssueService = bloodIssueService;
    }

    @Transactional
    public BloodRequest createRequest(BloodRequestDto dto) {
        User patient = userService.getUserById(dto.getPatientId());

        BloodRequest request = new BloodRequest();
        request.setPatient(patient);
        request.setPatientName(dto.getPatientName() != null ? dto.getPatientName().trim() : patient.getFullName());
        request.setBloodGroup(dto.getBloodGroup().toUpperCase().trim());
        request.setUnitsRequested(dto.getUnitsRequested());
        request.setHospitalName(dto.getHospitalName().trim());
        request.setDoctorName(dto.getDoctorName());
        request.setReason(dto.getReason());
        request.setUrgency(dto.getUrgency() != null ? dto.getUrgency() : UrgencyLevel.NORMAL);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestDate(dto.getRequestDate() != null ? dto.getRequestDate() : LocalDate.now());

        return bloodRequestRepository.save(request);
    }

    public List<BloodRequest> getAllRequests() {
        return bloodRequestRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<BloodRequest> getRequestsByPatientId(Long patientId) {
        return bloodRequestRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public BloodRequest getRequestById(Long id) {
        return bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request with ID " + id + " not found"));
    }

    @Transactional
    public BloodRequest updateRequestStatus(Long requestId, RequestStatus newStatus, String rejectionReason) {
        BloodRequest request = getRequestById(requestId);
        request.setStatus(newStatus);
        if (rejectionReason != null && !rejectionReason.isBlank()) {
            request.setRejectionReason(rejectionReason);
        }
        return bloodRequestRepository.save(request);
    }

    @Transactional
    public BloodIssue issueBloodForRequest(Long requestId, String adminUsername, String notes) {
        BloodRequest request = getRequestById(requestId);

        if (request.getStatus() == RequestStatus.ISSUED) {
            throw new IllegalArgumentException("Blood has already been issued for this request.");
        }

        // Deduct inventory
        bloodStockService.decrementStock(request.getBloodGroup(), request.getUnitsRequested());

        // Update request status to ISSUED
        request.setStatus(RequestStatus.ISSUED);
        bloodRequestRepository.save(request);

        // Record issuance
        return bloodIssueService.recordIssue(request, adminUsername, notes);
    }
}
