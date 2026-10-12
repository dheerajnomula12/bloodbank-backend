package com.bloodbank.config;

import com.bloodbank.entity.*;
import com.bloodbank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BloodStockRepository bloodStockRepository;
    private final DonationRepository donationRepository;
    private final BloodRequestRepository bloodRequestRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           BloodStockRepository bloodStockRepository,
                           DonationRepository donationRepository,
                           BloodRequestRepository bloodRequestRepository) {
        this.userRepository = userRepository;
        this.bloodStockRepository = bloodStockRepository;
        this.donationRepository = donationRepository;
        this.bloodRequestRepository = bloodRequestRepository;
    }

    @Override
    public void run(String... args) {
        initBloodStock();
        initUsers();
        initDonations();
        initRequests();
    }

    private void initBloodStock() {
        List<String> groups = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        int[] initialUnits = {42, 16, 38, 11, 24, 8, 62, 14};
        int[] thresholds = {15, 10, 15, 10, 10, 10, 20, 15};

        for (int i = 0; i < groups.size(); i++) {
            String bg = groups.get(i);
            if (!bloodStockRepository.existsByBloodGroup(bg)) {
                BloodStock stock = new BloodStock(null, bg, initialUnits[i], thresholds[i]);
                bloodStockRepository.save(stock);
            }
        }
    }

    private void initUsers() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User(null, "admin", "admin123", "Chief Administrator",
                    "admin@bloodbank.org", "+1 (555) 019-2831", Role.ROLE_ADMIN,
                    "O+", 38, "Male", "Central Blood Bank HQ, Suite 400", "Metro City");
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("donor_alex")) {
            User alex = new User(null, "donor_alex", "donor123", "Alex Morgan",
                    "alex.morgan@example.com", "+1 (555) 234-5678", Role.ROLE_DONOR,
                    "O+", 28, "Male", "742 Evergreen Terrace", "Springfield");
            userRepository.save(alex);
        }

        if (!userRepository.existsByUsername("donor_priya")) {
            User priya = new User(null, "donor_priya", "donor123", "Priya Sharma",
                    "priya.s@example.com", "+1 (555) 876-5432", Role.ROLE_DONOR,
                    "B+", 25, "Female", "45 Ocean View Drive", "Metro City");
            userRepository.save(priya);
        }

        if (!userRepository.existsByUsername("donor_marcus")) {
            User marcus = new User(null, "donor_marcus", "donor123", "Marcus Vance",
                    "marcus.vance@example.com", "+1 (555) 345-6789", Role.ROLE_DONOR,
                    "A-", 32, "Male", "12 Maple Avenue", "Riverdale");
            userRepository.save(marcus);
        }

        if (!userRepository.existsByUsername("patient_sarah")) {
            User sarah = new User(null, "patient_sarah", "patient123", "Sarah Jenkins",
                    "sarah.j@example.com", "+1 (555) 987-6543", Role.ROLE_PATIENT,
                    "O-", 29, "Female", "88 Sunset Blvd", "Metro City");
            userRepository.save(sarah);
        }

        if (!userRepository.existsByUsername("patient_david")) {
            User david = new User(null, "patient_david", "patient123", "David Kim",
                    "david.kim@example.com", "+1 (555) 456-7890", Role.ROLE_PATIENT,
                    "AB+", 45, "Male", "104 Highland Way", "Springfield");
            userRepository.save(david);
        }
    }

    private void initDonations() {
        if (donationRepository.count() == 0) {
            userRepository.findByUsername("donor_alex").ifPresent(alex -> {
                Donation d1 = new Donation(null, alex, "O+", 1, LocalDate.now().minusDays(20),
                        DonationStatus.COMPLETED, 14.8, 74.0, "120/80", "None", "City Central Blood Center");
                donationRepository.save(d1);
            });

            userRepository.findByUsername("donor_priya").ifPresent(priya -> {
                Donation d2 = new Donation(null, priya, "B+", 1, LocalDate.now().minusDays(10),
                        DonationStatus.COMPLETED, 13.5, 59.0, "118/76", "None", "Metropolitan Red Cross");
                donationRepository.save(d2);
            });

            userRepository.findByUsername("donor_marcus").ifPresent(marcus -> {
                Donation d3 = new Donation(null, marcus, "A-", 1, LocalDate.now().plusDays(2),
                        DonationStatus.PENDING, 14.2, 79.0, "122/82", "None", "Riverdale Community Clinic");
                donationRepository.save(d3);
            });
        }
    }

    private void initRequests() {
        if (bloodRequestRepository.count() == 0) {
            userRepository.findByUsername("patient_sarah").ifPresent(sarah -> {
                BloodRequest r1 = new BloodRequest(null, sarah, "Sarah Jenkins", "O-", 2,
                        "City General Hospital - Trauma ICU", "Dr. Evelyn Reed",
                        "Urgent blood transfusion required for emergency orthopedic trauma surgery.",
                        UrgencyLevel.CRITICAL, RequestStatus.APPROVED, LocalDate.now().minusDays(1));
                bloodRequestRepository.save(r1);
            });

            userRepository.findByUsername("patient_david").ifPresent(david -> {
                BloodRequest r2 = new BloodRequest(null, david, "David Kim", "AB+", 1,
                        "Riverdale Memorial Hospital", "Dr. Robert Chen",
                        "Pre-operative preparation for scheduled cardiovascular surgery.",
                        UrgencyLevel.NORMAL, RequestStatus.PENDING, LocalDate.now());
                bloodRequestRepository.save(r2);
            });
        }
    }
}
