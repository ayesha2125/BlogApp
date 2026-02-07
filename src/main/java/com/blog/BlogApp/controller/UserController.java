package com.blog.BlogApp.controller;

import com.blog.BlogApp.model.User;
import com.blog.BlogApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;   // ✅ REQUIRED
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepo;

    // ----------- REGISTER -----------
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // ✅ Email validation
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Email is required");
            }

            // ✅ Password validation
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Password is required");
            }

            // ✅ Check duplicate email
            if (userRepo.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already registered");
            }

            // ✅ Set defaults
            user.setRole("USER");
            user.setCreatedAt(LocalDateTime.now());

            User savedUser = userRepo.save(user);

            // ✅ Never expose password
            savedUser.setPassword(null);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            logger.error("Registration failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed");
        }
    }

    // ----------- LOGIN -----------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User existingUser = userRepo.findByEmail(user.getEmail());

            if (existingUser != null &&
                    existingUser.getPassword().equals(user.getPassword())) {

                existingUser.setPassword(null);
                return ResponseEntity.ok(existingUser);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");

        } catch (Exception e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed");
        }
    }

    // ----------- GET ALL USERS -----------
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        users.forEach(u -> u.setPassword(null));
        return ResponseEntity.ok(users);
    }
}