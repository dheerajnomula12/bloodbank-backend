package com.bloodbank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_issues")
public class BloodIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = false)
    private BloodRequest request;

    @Column(name = "blood_group", nullable = false, length = 10)
    private String bloodGroup;

    @Column(name = "units_issued", nullable = false)
    private Integer unitsIssued;

    @Column(name = "issued_to", nullable = false, length = 100)
    private String issuedTo;

    @Column(name = "hospital_name", nullable = false, length = 150)
    private String hospitalName;

    @Column(name = "issued_by_admin", nullable = false, length = 100)
    private String issuedByAdmin;

    @Column(name = "certificate_number", nullable = false, unique = true, length = 50)
    private String certificateNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @PrePersist
    protected void onCreate() {
        if (issueDate == null) {
            issueDate = LocalDateTime.now();
        }
    }

    public BloodIssue() {}

    public BloodIssue(Long id, BloodRequest request, String bloodGroup, Integer unitsIssued,
                      String issuedTo, String hospitalName, String issuedByAdmin,
                      String certificateNumber, String notes) {
        this.id = id;
        this.request = request;
        this.bloodGroup = bloodGroup;
        this.unitsIssued = unitsIssued;
        this.issuedTo = issuedTo;
        this.hospitalName = hospitalName;
        this.issuedByAdmin = issuedByAdmin;
        this.certificateNumber = certificateNumber;
        this.notes = notes;
        this.issueDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BloodRequest getRequest() { return request; }
    public void setRequest(BloodRequest request) { this.request = request; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public Integer getUnitsIssued() { return unitsIssued; }
    public void setUnitsIssued(Integer unitsIssued) { this.unitsIssued = unitsIssued; }

    public String getIssuedTo() { return issuedTo; }
    public void setIssuedTo(String issuedTo) { this.issuedTo = issuedTo; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public String getIssuedByAdmin() { return issuedByAdmin; }
    public void setIssuedByAdmin(String issuedByAdmin) { this.issuedByAdmin = issuedByAdmin; }

    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
