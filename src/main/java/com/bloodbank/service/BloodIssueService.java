package com.bloodbank.service;

import com.bloodbank.entity.BloodIssue;
import com.bloodbank.entity.BloodRequest;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.BloodIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class BloodIssueService {

    private final BloodIssueRepository bloodIssueRepository;

    @Autowired
    public BloodIssueService(BloodIssueRepository bloodIssueRepository) {
        this.bloodIssueRepository = bloodIssueRepository;
    }

    @Transactional
    public BloodIssue recordIssue(BloodRequest request, String adminUsername, String notes) {
        String certNum = "ISS-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" +
                UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        BloodIssue issue = new BloodIssue();
        issue.setRequest(request);
        issue.setBloodGroup(request.getBloodGroup());
        issue.setUnitsIssued(request.getUnitsRequested());
        issue.setIssuedTo(request.getPatientName());
        issue.setHospitalName(request.getHospitalName());
        issue.setIssuedByAdmin(adminUsername != null ? adminUsername : "System Admin");
        issue.setCertificateNumber(certNum);
        issue.setNotes(notes != null ? notes : "Blood dispatched securely under standard cold-chain protocol.");

        return bloodIssueRepository.save(issue);
    }

    public List<BloodIssue> getAllIssues() {
        return bloodIssueRepository.findAllByOrderByIssueDateDesc();
    }

    public BloodIssue getIssueById(Long id) {
        return bloodIssueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood issue record with ID " + id + " not found"));
    }

    public BloodIssue getIssueByCertificateNumber(String certNumber) {
        return bloodIssueRepository.findByCertificateNumber(certNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate " + certNumber + " not found"));
    }

    public Long getTotalUnitsIssued() {
        Long total = bloodIssueRepository.getTotalIssuedUnits();
        return total != null ? total : 0L;
    }
}
