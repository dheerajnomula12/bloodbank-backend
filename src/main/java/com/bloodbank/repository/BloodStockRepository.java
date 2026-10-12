package com.bloodbank.repository;

import com.bloodbank.entity.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodStockRepository extends JpaRepository<BloodStock, Long> {
    Optional<BloodStock> findByBloodGroup(String bloodGroup);
    boolean existsByBloodGroup(String bloodGroup);

    @Query("SELECT SUM(b.unitsAvailable) FROM BloodStock b")
    Long getTotalAvailableUnits();
}
