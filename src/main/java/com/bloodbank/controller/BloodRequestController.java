package com.bloodbank.controller;

import com.bloodbank.dto.ApiResponse;
import com.bloodbank.dto.BloodRequestDto;
import com.bloodbank.entity.BloodIssue;
import com.bloodbank.entity.BloodRequest;
import com.bloodbank.entity.RequestStatus;
import com.bloodbank.service.BloodRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    @Autowired
    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BloodRequest>> createRequest(@Valid @RequestBody BloodRequestDto dto) {
        BloodRequest created = bloodRequestService.createRequest(dto);
        return ResponseEntity.ok(ApiResponse.ok("Blood request submitted successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BloodRequest>>> getAllRequests() {
        List<BloodRequest> list = bloodRequestService.getAllRequests();
        return ResponseEntity.ok(ApiResponse.ok("Blood requests retrieved successfully", list));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<BloodRequest>>> getRequestsByPatient(@PathVariable Long patientId) {
        List<BloodRequest> list = bloodRequestService.getRequestsByPatientId(patientId);
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BloodRequest>> getRequestById(@PathVariable Long id) {
        BloodRequest request = bloodRequestService.getRequestById(id);
        return ResponseEntity.ok(ApiResponse.ok(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<BloodRequest>> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        String rejectionReason = payload.get("rejectionReason");

        if (statusStr == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Status field is required"));
        }

        RequestStatus status = RequestStatus.valueOf(statusStr.toUpperCase());
        BloodRequest updated = bloodRequestService.updateRequestStatus(id, status, rejectionReason);
        return ResponseEntity.ok(ApiResponse.ok("Blood request status updated to " + status, updated));
    }

    @PostMapping("/{id}/issue")
    public ResponseEntity<ApiResponse<BloodIssue>> issueBlood(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> payload) {
        String adminUsername = payload != null ? payload.get("adminUsername") : "Admin Staff";
        String notes = payload != null ? payload.get("notes") : "Blood dispatched securely.";

        BloodIssue issue = bloodRequestService.issueBloodForRequest(id, adminUsername, notes);
        return ResponseEntity.ok(ApiResponse.ok("Blood units successfully issued & deducted from inventory!", issue));
    }
}
