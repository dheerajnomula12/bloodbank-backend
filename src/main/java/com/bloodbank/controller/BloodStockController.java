package com.bloodbank.controller;

import com.bloodbank.dto.ApiResponse;
import com.bloodbank.dto.BloodStockUpdateDto;
import com.bloodbank.entity.BloodStock;
import com.bloodbank.service.BloodStockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class BloodStockController {

    private final BloodStockService bloodStockService;

    @Autowired
    public BloodStockController(BloodStockService bloodStockService) {
        this.bloodStockService = bloodStockService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BloodStock>>> getAllStock() {
        List<BloodStock> stockList = bloodStockService.getAllStock();
        return ResponseEntity.ok(ApiResponse.ok("Blood stock retrieved successfully", stockList));
    }

    @GetMapping("/{bloodGroup}")
    public ResponseEntity<ApiResponse<BloodStock>> getStockByBloodGroup(@PathVariable String bloodGroup) {
        BloodStock stock = bloodStockService.getStockByBloodGroup(bloodGroup);
        return ResponseEntity.ok(ApiResponse.ok(stock));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<BloodStock>> updateStock(@Valid @RequestBody BloodStockUpdateDto dto) {
        BloodStock updated = bloodStockService.updateStock(dto);
        return ResponseEntity.ok(ApiResponse.ok("Blood stock updated successfully", updated));
    }

    @GetMapping("/check-availability")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailability(
            @RequestParam String bloodGroup,
            @RequestParam(defaultValue = "1") int units) {
        boolean available = bloodStockService.isAvailable(bloodGroup, units);
        return ResponseEntity.ok(ApiResponse.ok("Availability checked", available));
    }
}
