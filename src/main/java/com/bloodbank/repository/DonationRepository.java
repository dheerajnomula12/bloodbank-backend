package com.bloodbank.repository;

import com.bloodbank.entity.Donation;
import com.bloodbank.entity.DonationStatus;
import com.bloodbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorOrderByCreatedAtDesc(User donor);
    List<Donation> findByDonorIdOrderByCreatedAtDesc(Long donorId);
    List<Donation> findByStatusOrderByCreatedAtDesc(DonationStatus status);
    List<Donation> findAllByOrderByCreatedAtDesc();
    long countByStatus(DonationStatus status);
}
