package com.bloodbank.service;

import com.bloodbank.dto.DashboardStatsDto;
import com.bloodbank.entity.BloodStock;
import com.bloodbank.entity.DonationStatus;
import com.bloodbank.entity.RequestStatus;
import com.bloodbank.entity.Role;
import com.bloodbank.repository.BloodIssueRepository;
import com.bloodbank.repository.BloodRequestRepository;
import com.bloodbank.repository.BloodStockRepository;
import com.bloodbank.repository.DonationRepository;
import com.bloodbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final BloodStockRepository bloodStockRepository;
    private final DonationRepository donationRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final BloodIssueRepository bloodIssueRepository;

    @Autowired
    public DashboardService(UserRepository userRepository,
                            BloodStockRepository bloodStockRepository,
                            DonationRepository donationRepository,
                            BloodRequestRepository bloodRequestRepository,
                            BloodIssueRepository bloodIssueRepository) {
        this.userRepository = userRepository;
        this.bloodStockRepository = bloodStockRepository;
        this.donationRepository = donationRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.bloodIssueRepository = bloodIssueRepository;
    }

    public DashboardStatsDto getDashboardStats() {
        DashboardStatsDto stats = new DashboardStatsDto();

        stats.setTotalDonors(userRepository.countByRole(Role.ROLE_DONOR));
        stats.setTotalPatients(userRepository.countByRole(Role.ROLE_PATIENT));

        Long totalStock = bloodStockRepository.getTotalAvailableUnits();
        stats.setTotalAvailableUnits(totalStock != null ? totalStock : 0L);

        stats.setTotalDonationsCompleted(donationRepository.countByStatus(DonationStatus.COMPLETED));
        stats.setPendingDonations(donationRepository.countByStatus(DonationStatus.PENDING));

        stats.setTotalRequests(bloodRequestRepository.count());
        stats.setPendingRequests(bloodRequestRepository.countByStatus(RequestStatus.PENDING));

        Long totalIssued = bloodIssueRepository.getTotalIssuedUnits();
        stats.setTotalUnitsIssued(totalIssued != null ? totalIssued : 0L);

        List<BloodStock> stocks = bloodStockRepository.findAll();
        Map<String, Integer> stockMap = new HashMap<>();
        for (BloodStock bs : stocks) {
            stockMap.put(bs.getBloodGroup(), bs.getUnitsAvailable());
        }
        stats.setStockByGroup(stockMap);

        return stats;
    }
}
