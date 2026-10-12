-- ==========================================================
-- Blood Bank Management System - Initial Seed Data
-- ==========================================================

-- Insert Default Admin & Sample Users
INSERT INTO users (id, username, password, full_name, email, phone, role, blood_group, age, gender, address, city)
VALUES 
(1, 'admin', 'admin123', 'System Administrator', 'admin@bloodbank.org', '+1 555-0199', 'ROLE_ADMIN', 'O+', 35, 'Other', 'Blood Bank Central HQ', 'Metro City'),
(2, 'donor_alex', 'donor123', 'Alex Morgan', 'alex.morgan@example.com', '+1 555-0142', 'ROLE_DONOR', 'O+', 28, 'Male', '742 Evergreen Terrace', 'Springfield'),
(3, 'donor_priya', 'donor123', 'Priya Sharma', 'priya.s@example.com', '+1 555-0188', 'ROLE_DONOR', 'B+', 24, 'Female', '45 Ocean View Drive', 'Metro City'),
(4, 'donor_marcus', 'donor123', 'Marcus Vance', 'marcus.v@example.com', '+1 555-0176', 'ROLE_DONOR', 'A-', 31, 'Male', '12 Maple Avenue', 'Springfield'),
(5, 'patient_sarah', 'patient123', 'Sarah Jenkins', 'sarah.j@example.com', '+1 555-0111', 'ROLE_PATIENT', 'O-', 29, 'Female', '88 Sunset Blvd', 'Metro City'),
(6, 'patient_david', 'patient123', 'David Kim', 'david.kim@example.com', '+1 555-0133', 'ROLE_PATIENT', 'AB+', 42, 'Male', '104 Highland Way', 'Riverdale')
ON DUPLICATE KEY UPDATE full_name=VALUES(full_name);

-- Initialize Blood Stock for all 8 standard blood groups
INSERT INTO blood_stock (id, blood_group, units_available, minimum_threshold)
VALUES
(1, 'A+', 45, 15),
(2, 'A-', 18, 10),
(3, 'B+', 38, 15),
(4, 'B-', 12, 10),
(5, 'AB+', 22, 10),
(6, 'AB-', 8, 10),
(7, 'O+', 65, 20),
(8, 'O-', 14, 15)
ON DUPLICATE KEY UPDATE units_available=VALUES(units_available);

-- Sample Donation Records
INSERT INTO donations (id, donor_id, blood_group, units, donation_date, status, hemoglobin, weight_kg, blood_pressure, hospital_center)
VALUES
(1, 2, 'O+', 1, '2026-09-15', 'COMPLETED', 14.5, 72.0, '120/80', 'Central City Blood Center'),
(2, 3, 'B+', 1, '2026-09-28', 'COMPLETED', 13.8, 58.5, '118/75', 'Metropolitan Red Cross'),
(3, 4, 'A-', 1, '2026-10-02', 'PENDING', 14.0, 78.0, '122/82', 'Metro General Blood Wing')
ON DUPLICATE KEY UPDATE status=VALUES(status);

-- Sample Blood Requests
INSERT INTO blood_requests (id, patient_id, patient_name, blood_group, units_requested, hospital_name, doctor_name, reason, urgency, status, request_date)
VALUES
(1, 5, 'Sarah Jenkins', 'O-', 2, 'City General Hospital', 'Dr. Evelyn Reed', 'Emergency orthopedic surgery following trauma', 'CRITICAL', 'APPROVED', '2026-10-06'),
(2, 6, 'David Kim', 'AB+', 1, 'Riverdale Memorial', 'Dr. Robert Chen', 'Scheduled surgical procedure', 'NORMAL', 'PENDING', '2026-10-07')
ON DUPLICATE KEY UPDATE status=VALUES(status);
