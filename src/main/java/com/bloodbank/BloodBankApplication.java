package com.bloodbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BloodBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloodBankApplication.class, args);
        System.out.println("==================================================================");
        System.out.println(" Blood Bank Management System Server is running on port 8080!");
        System.out.println(" Open http://localhost:8080 in your browser to access the Web UI.");
        System.out.println("==================================================================");
    }
}
