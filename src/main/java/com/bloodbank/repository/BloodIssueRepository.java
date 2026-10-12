package com.bloodbank.repository;

import com.bloodbank.entity.BloodIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloodIssueRepository extends JpaRepository<BloodIssue, Long> {
    List<BloodIssue> findAllByOrderByIssueDateDesc();
    Optional<BloodIssue> findByCertificateNumber(String certificateNumber);

    @Query("SELECT SUM(i.unitsIssued) FROM BloodIssue i")
    Long getTotalIssuedUnits();
}
