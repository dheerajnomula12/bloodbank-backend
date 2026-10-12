-- ==========================================================
-- Blood Bank Management System - Database Schema (MySQL)
-- Database: blood_bank_db
-- ==========================================================

CREATE DATABASE IF NOT EXISTS blood_bank_db;
USE blood_bank_db;

-- 1. Users Table (Admin, Donors, Patients)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL, -- ROLE_ADMIN, ROLE_DONOR, ROLE_PATIENT
    blood_group VARCHAR(10),   -- A+, A-, B+, B-, AB+, AB-, O+, O-
    age INT,
    gender VARCHAR(15),
    address VARCHAR(255),
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Blood Stock Inventory Table
CREATE TABLE IF NOT EXISTS blood_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blood_group VARCHAR(10) NOT NULL UNIQUE,
    units_available INT NOT NULL DEFAULT 0,
    minimum_threshold INT NOT NULL DEFAULT 10,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Blood Donations Table
CREATE TABLE IF NOT EXISTS donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id BIGINT NOT NULL,
    blood_group VARCHAR(10) NOT NULL,
    units INT NOT NULL DEFAULT 1,
    donation_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, APPROVED, COMPLETED, REJECTED
    hemoglobin DECIMAL(4,1),
    weight_kg DECIMAL(5,1),
    blood_pressure VARCHAR(20),
    disease_history TEXT,
    rejection_reason TEXT,
    hospital_center VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Blood Requests Table
CREATE TABLE IF NOT EXISTS blood_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    patient_name VARCHAR(100) NOT NULL,
    blood_group VARCHAR(10) NOT NULL,
    units_requested INT NOT NULL,
    hospital_name VARCHAR(150) NOT NULL,
    doctor_name VARCHAR(100),
    reason TEXT NOT NULL,
    urgency VARCHAR(20) NOT NULL DEFAULT 'NORMAL', -- NORMAL, URGENT, CRITICAL
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- PENDING, APPROVED, ISSUED, REJECTED
    request_date DATE NOT NULL,
    rejection_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5. Blood Issue Records Table
CREATE TABLE IF NOT EXISTS blood_issues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id BIGINT NOT NULL,
    blood_group VARCHAR(10) NOT NULL,
    units_issued INT NOT NULL,
    issued_to VARCHAR(100) NOT NULL,
    hospital_name VARCHAR(150) NOT NULL,
    issued_by_admin VARCHAR(100) NOT NULL,
    certificate_number VARCHAR(50) NOT NULL UNIQUE,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    FOREIGN KEY (request_id) REFERENCES blood_requests(id) ON DELETE CASCADE
);
