package com.bloodbank.dto;

import java.util.Map;

public class DashboardStatsDto {
    private long totalDonors;
    private long totalPatients;
    private long totalAvailableUnits;
    private long totalDonationsCompleted;
    private long pendingDonations;
    private long totalRequests;
    private long pendingRequests;
    private long totalUnitsIssued;
    private Map<String, Integer> stockByGroup;

    public DashboardStatsDto() {}

    public long getTotalDonors() { return totalDonors; }
    public void setTotalDonors(long totalDonors) { this.totalDonors = totalDonors; }

    public long getTotalPatients() { return totalPatients; }
    public void setTotalPatients(long totalPatients) { this.totalPatients = totalPatients; }

    public long getTotalAvailableUnits() { return totalAvailableUnits; }
    public void setTotalAvailableUnits(long totalAvailableUnits) { this.totalAvailableUnits = totalAvailableUnits; }

    public long getTotalDonationsCompleted() { return totalDonationsCompleted; }
    public void setTotalDonationsCompleted(long totalDonationsCompleted) { this.totalDonationsCompleted = totalDonationsCompleted; }

    public long getPendingDonations() { return pendingDonations; }
    public void setPendingDonations(long pendingDonations) { this.pendingDonations = pendingDonations; }

    public long getTotalRequests() { return totalRequests; }
    public void setTotalRequests(long totalRequests) { this.totalRequests = totalRequests; }

    public long getPendingRequests() { return pendingRequests; }
    public void setPendingRequests(long pendingRequests) { this.pendingRequests = pendingRequests; }

    public long getTotalUnitsIssued() { return totalUnitsIssued; }
    public void setTotalUnitsIssued(long totalUnitsIssued) { this.totalUnitsIssued = totalUnitsIssued; }

    public Map<String, Integer> getStockByGroup() { return stockByGroup; }
    public void setStockByGroup(Map<String, Integer> stockByGroup) { this.stockByGroup = stockByGroup; }
}
