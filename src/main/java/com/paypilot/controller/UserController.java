/**
 * Author: Shrilakshmi Upadhya
 * Date: 6th September 2024
 * 
 * Description: This is the controller file for various endpoints related to sign-up, login and OTP handling.
 */

package com.paypilot.controller;

import com.paypilot.dto.OtpVerificationRequest;
import com.paypilot.dto.PasswordResetRequest;
import com.paypilot.dto.UserVerificationRequest;
import com.paypilot.model.User;
import com.paypilot.service.OtpService;
import com.paypilot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    // Sign up endpoint
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        Long count = userRepository.countByUserId(user.getUserId());
        if (count > 0) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        User u = userRepository.save(user);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    // Endpoint to check if a user exists by userId
    @GetMapping("/con/{userId}")
    public Long con(@PathVariable String userId) {
        return userRepository.countByUserId(userId);
    }

    // Endpoint to check if a user exists by email
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String input = credentials.get("input");
        String password = credentials.get("password");

        Optional<User> userOptional;

        // Check if input is email or userId
        if (input.contains("@")) {
            userOptional = userRepository.findByEmail(input);
        } else {
            userOptional = userRepository.findByUserId(input);
        }

        Map<String, String> response = new HashMap<>();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                // Return email in the response if login is successful
                response.put("message", "Login successful");
                response.put("email", user.getEmail());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Incorrect password");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    // Send OTP to email
    @PostMapping("/send-otp/{input}")
    public ResponseEntity<String> sendOtp(@PathVariable String input) {
        Optional<User> userOptional;

        // Check if input is an email
        if (input.contains("@")) {
            userOptional = userRepository.findByEmail(input);
        } else {
            userOptional = userRepository.findByUserId(input);
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            otpService.generateAndSendOtp(user.getEmail());
            return new ResponseEntity<>("OTP sent to email", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/verify-user")
    public ResponseEntity<?> verifyUser(@RequestBody UserVerificationRequest request) {
        // Fetch user by email and userId
        User user = userRepository.findByEmailAndUserId(request.getEmail(), request.getUserId());
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // If user is found, send OTP to user's email and proceed
        return ResponseEntity.ok("User verified");
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        // Find the user by email
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        
     // Update the user's password
    	user.get().setPassword(request.getNewPassword());
        userRepository.save(user.get());
        return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody OtpVerificationRequest otpRequest) {
        boolean isValid = otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());

        Map<String, String> response = new HashMap<>();

        if (isValid) {
//            response.put("success", "true");
//            response.put("message", "OTP Verified");
//            return new ResponseEntity<>(response, HttpStatus.OK);
        	
        	// Fetch the user details using the email
            Optional<User> userOptional = userRepository.findByEmail(otpRequest.getEmail());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                response.put("success", "true");
                response.put("message", "OTP Verified");
                response.put("userId", user.getUserId()); // Include userId in the response
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // If user is not found by email (although OTP was valid, it should ideally not happen- but just in case)
                response.put("success", "false");
                response.put("message", "User not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            response.put("success", "false");
            response.put("message", "Invalid OTP or OTP expired");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}

