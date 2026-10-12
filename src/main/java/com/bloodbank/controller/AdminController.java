package com.bloodbank.controller;

import com.bloodbank.dto.ApiResponse;
import com.bloodbank.dto.DashboardStatsDto;
import com.bloodbank.entity.User;
import com.bloodbank.service.DashboardService;
import com.bloodbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final DashboardService dashboardService;
    private final UserService userService;

    @Autowired
    public AdminController(DashboardService dashboardService, UserService userService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        DashboardStatsDto stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.ok("Dashboard statistics retrieved", stats));
    }

    @GetMapping("/donors")
    public ResponseEntity<ApiResponse<List<User>>> getDonors() {
        List<User> donors = userService.getAllDonors();
        return ResponseEntity.ok(ApiResponse.ok("Registered donors retrieved", donors));
    }

    @GetMapping("/patients")
    public ResponseEntity<ApiResponse<List<User>>> getPatients() {
        List<User> patients = userService.getAllPatients();
        return ResponseEntity.ok(ApiResponse.ok("Registered patients retrieved", patients));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ok("All users retrieved", users));
    }
}
