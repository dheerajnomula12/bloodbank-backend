@echo off
title PulseLife Blood Bank - Backend Server
echo ========================================================
echo   Starting PulseLife Blood Bank Backend (Spring Boot)
echo ========================================================
echo.
echo   API Base URL: http://localhost:8080/api
echo   Database: MySQL (blood_bank_db on port 3306)
echo.
mvn spring-boot:run
pause
