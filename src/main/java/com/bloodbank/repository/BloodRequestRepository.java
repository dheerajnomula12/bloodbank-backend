package com.bloodbank.repository;

import com.bloodbank.entity.BloodRequest;
import com.bloodbank.entity.RequestStatus;
import com.bloodbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByPatientOrderByCreatedAtDesc(User patient);
    List<BloodRequest> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    List<BloodRequest> findByStatusOrderByCreatedAtDesc(RequestStatus status);
    List<BloodRequest> findAllByOrderByCreatedAtDesc();
    long countByStatus(RequestStatus status);
}
