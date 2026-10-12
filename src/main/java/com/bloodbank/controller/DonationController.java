package com.bloodbank.controller;

import com.bloodbank.dto.ApiResponse;
import com.bloodbank.dto.DonationDto;
import com.bloodbank.entity.Donation;
import com.bloodbank.entity.DonationStatus;
import com.bloodbank.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    private final DonationService donationService;

    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Donation>> createDonation(@Valid @RequestBody DonationDto dto) {
        Donation donation = donationService.createDonation(dto);
        return ResponseEntity.ok(ApiResponse.ok("Blood donation appointment registered successfully", donation));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Donation>>> getAllDonations() {
        List<Donation> list = donationService.getAllDonations();
        return ResponseEntity.ok(ApiResponse.ok("Donations retrieved successfully", list));
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<ApiResponse<List<Donation>>> getDonationsByDonor(@PathVariable Long donorId) {
        List<Donation> list = donationService.getDonationsByDonorId(donorId);
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Donation>> getDonationById(@PathVariable Long id) {
        Donation donation = donationService.getDonationById(id);
        return ResponseEntity.ok(ApiResponse.ok(donation));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Donation>> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        String rejectionReason = payload.get("rejectionReason");

        if (statusStr == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Status field is required"));
        }

        DonationStatus status = DonationStatus.valueOf(statusStr.toUpperCase());
        Donation updated = donationService.updateDonationStatus(id, status, rejectionReason);
        return ResponseEntity.ok(ApiResponse.ok("Donation status updated to " + status, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.ok(ApiResponse.ok("Donation record deleted", null));
    }
}
