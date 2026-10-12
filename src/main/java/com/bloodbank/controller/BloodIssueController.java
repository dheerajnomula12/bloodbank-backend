package com.bloodbank.controller;

import com.bloodbank.dto.ApiResponse;
import com.bloodbank.entity.BloodIssue;
import com.bloodbank.service.BloodIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*")
public class BloodIssueController {

    private final BloodIssueService bloodIssueService;

    @Autowired
    public BloodIssueController(BloodIssueService bloodIssueService) {
        this.bloodIssueService = bloodIssueService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BloodIssue>>> getAllIssues() {
        List<BloodIssue> list = bloodIssueService.getAllIssues();
        return ResponseEntity.ok(ApiResponse.ok("Blood issue logs retrieved successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BloodIssue>> getIssueById(@PathVariable Long id) {
        BloodIssue issue = bloodIssueService.getIssueById(id);
        return ResponseEntity.ok(ApiResponse.ok(issue));
    }

    @GetMapping("/certificate/{certNumber}")
    public ResponseEntity<ApiResponse<BloodIssue>> getIssueByCertificate(@PathVariable String certNumber) {
        BloodIssue issue = bloodIssueService.getIssueByCertificateNumber(certNumber);
        return ResponseEntity.ok(ApiResponse.ok(issue));
    }
}
