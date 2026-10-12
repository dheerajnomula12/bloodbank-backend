# PulseLife — Backend REST API

Java Spring Boot backend service for the **Blood Bank Management System**.

## 📁 Architecture & Packaging
```
backend/
├── pom.xml                                      # Maven project configuration
├── run-backend.bat                              # 1-click Spring Boot launcher
└── src/
    └── main/
        ├── java/com/bloodbank/
        │   ├── BloodBankApplication.java        # Spring Boot main class
        │   ├── config/                          # DataInitializer, WebConfig (CORS enabled)
        │   ├── controller/                      # REST Controllers (Auth, Stock, Donation, Request, Issue, Admin)
        │   ├── dto/                             # Request & Response DTOs
        │   ├── entity/                          # JPA Entities (User, BloodStock, Donation, BloodRequest, BloodIssue)
        │   ├── exception/                       # Custom exceptions & GlobalExceptionHandler
        │   ├── repository/                      # Spring Data JPA Repositories
        │   └── service/                         # Business Logic & automated inventory stock calculations
        └── resources/
            ├── application.properties          # MySQL datasource & JPA configuration
            ├── schema.sql                      # MySQL DDL relational table schema
            └── data.sql                        # Seed dataset
```

## 🚀 How to Run the Backend
1. Ensure MySQL is running on `localhost:3306` with database `blood_bank_db`.
2. Configure username/password in `src/main/resources/application.properties` (default: `root`/`root`).
3. Double-click `run-backend.bat` or run:
   ```bash
   mvn spring-boot:run
   ```
4. Backend will start on port **8080** (`http://localhost:8080/api`).

## 📡 REST API Summary
- **Auth**: `POST /api/auth/login`, `POST /api/auth/register`, `GET /api/auth/user/{id}`
- **Stock**: `GET /api/stock`, `GET /api/stock/{bloodGroup}`, `PUT /api/stock/update`, `GET /api/stock/check-availability`
- **Donations**: `GET /api/donations`, `POST /api/donations`, `PATCH /api/donations/{id}/status`
- **Requests**: `GET /api/requests`, `POST /api/requests`, `PATCH /api/requests/{id}/status`, `POST /api/requests/{id}/issue`
- **Issues**: `GET /api/issues`, `GET /api/issues/{id}`, `GET /api/issues/certificate/{certNumber}`
- **Admin**: `GET /api/admin/dashboard`, `GET /api/admin/donors`, `GET /api/admin/patients`, `GET /api/admin/users`
