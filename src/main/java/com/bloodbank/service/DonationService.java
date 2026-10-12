package com.bloodbank.service;

import com.bloodbank.dto.DonationDto;
import com.bloodbank.entity.Donation;
import com.bloodbank.entity.DonationStatus;
import com.bloodbank.entity.User;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserService userService;
    private final BloodStockService bloodStockService;

    @Autowired
    public DonationService(DonationRepository donationRepository,
                           UserService userService,
                           BloodStockService bloodStockService) {
        this.donationRepository = donationRepository;
        this.userService = userService;
        this.bloodStockService = bloodStockService;
    }

    @Transactional
    public Donation createDonation(DonationDto dto) {
        User donor = userService.getUserById(dto.getDonorId());

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setBloodGroup(dto.getBloodGroup() != null ? dto.getBloodGroup().toUpperCase() : donor.getBloodGroup());
        donation.setUnits(dto.getUnits() != null ? dto.getUnits() : 1);
        donation.setDonationDate(dto.getDonationDate() != null ? dto.getDonationDate() : LocalDate.now());
        donation.setStatus(dto.getStatus() != null ? dto.getStatus() : DonationStatus.PENDING);
        donation.setHemoglobin(dto.getHemoglobin());
        donation.setWeightKg(dto.getWeightKg());
        donation.setBloodPressure(dto.getBloodPressure());
        donation.setDiseaseHistory(dto.getDiseaseHistory());
        donation.setHospitalCenter(dto.getHospitalCenter() != null ? dto.getHospitalCenter() : "Central Blood Bank");

        Donation saved = donationRepository.save(donation);

        // If directly registered as COMPLETED, increment stock
        if (saved.getStatus() == DonationStatus.COMPLETED) {
            bloodStockService.incrementStock(saved.getBloodGroup(), saved.getUnits());
        }

        return saved;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Donation> getDonationsByDonorId(Long donorId) {
        return donationRepository.findByDonorIdOrderByCreatedAtDesc(donorId);
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation record with ID " + id + " not found"));
    }

    @Transactional
    public Donation updateDonationStatus(Long donationId, DonationStatus newStatus, String rejectionReason) {
        Donation donation = getDonationById(donationId);
        DonationStatus oldStatus = donation.getStatus();

        donation.setStatus(newStatus);
        if (rejectionReason != null && !rejectionReason.isBlank()) {
            donation.setRejectionReason(rejectionReason);
        }

        Donation saved = donationRepository.save(donation);

        // If transitioning to COMPLETED from a non-completed status, increment stock
        if (newStatus == DonationStatus.COMPLETED && oldStatus != DonationStatus.COMPLETED) {
            bloodStockService.incrementStock(saved.getBloodGroup(), saved.getUnits());
        }

        return saved;
    }

    @Transactional
    public void deleteDonation(Long id) {
        Donation donation = getDonationById(id);
        donationRepository.delete(donation);
    }
}
