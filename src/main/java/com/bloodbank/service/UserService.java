package com.bloodbank.service;

import com.bloodbank.dto.AuthResponse;
import com.bloodbank.dto.LoginRequest;
import com.bloodbank.dto.RegisterRequest;
import com.bloodbank.entity.Role;
import com.bloodbank.entity.User;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username '" + request.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setPassword(request.getPassword()); // Plain/BCrypt
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhone(request.getPhone().trim());
        user.setRole(request.getRole() != null ? request.getRole() : Role.ROLE_DONOR);
        user.setBloodGroup(request.getBloodGroup());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());

        User savedUser = userRepository.save(user);
        String mockToken = "bb_token_" + UUID.randomUUID().toString().substring(0, 18);
        return new AuthResponse(savedUser, mockToken);
    }

    public AuthResponse login(LoginRequest request) {
        String identifier = request.getUsername().trim();
        User user = userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String mockToken = "bb_token_" + UUID.randomUUID().toString().substring(0, 18);
        return new AuthResponse(user, mockToken);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    public List<User> getAllDonors() {
        return userRepository.findByRole(Role.ROLE_DONOR);
    }

    public List<User> getAllPatients() {
        return userRepository.findByRole(Role.ROLE_PATIENT);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
