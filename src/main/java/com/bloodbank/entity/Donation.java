package com.bloodbank.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @Column(name = "blood_group", nullable = false, length = 10)
    private String bloodGroup;

    @Column(nullable = false)
    private Integer units = 1;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DonationStatus status = DonationStatus.PENDING;

    private Double hemoglobin;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    @Column(name = "disease_history", columnDefinition = "TEXT")
    private String diseaseHistory;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "hospital_center", length = 150)
    private String hospitalCenter;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (donationDate == null) {
            donationDate = LocalDate.now();
        }
    }

    public Donation() {}

    public Donation(Long id, User donor, String bloodGroup, Integer units, LocalDate donationDate,
                    DonationStatus status, Double hemoglobin, Double weightKg, String bloodPressure,
                    String diseaseHistory, String hospitalCenter) {
        this.id = id;
        this.donor = donor;
        this.bloodGroup = bloodGroup;
        this.units = units != null ? units : 1;
        this.donationDate = donationDate;
        this.status = status != null ? status : DonationStatus.PENDING;
        this.hemoglobin = hemoglobin;
        this.weightKg = weightKg;
        this.bloodPressure = bloodPressure;
        this.diseaseHistory = diseaseHistory;
        this.hospitalCenter = hospitalCenter;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public Integer getUnits() { return units; }
    public void setUnits(Integer units) { this.units = units; }

    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }

    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }

    public Double getHemoglobin() { return hemoglobin; }
    public void setHemoglobin(Double hemoglobin) { this.hemoglobin = hemoglobin; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public String getDiseaseHistory() { return diseaseHistory; }
    public void setDiseaseHistory(String diseaseHistory) { this.diseaseHistory = diseaseHistory; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getHospitalCenter() { return hospitalCenter; }
    public void setHospitalCenter(String hospitalCenter) { this.hospitalCenter = hospitalCenter; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
