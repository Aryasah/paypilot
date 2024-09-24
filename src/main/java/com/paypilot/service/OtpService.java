/**
 * Author: Shrilakshmi Upadhya
 * Date: 6th September 2024
 * 
 * Description: This is the file to provide service for OTP generation and handling.
 */

package com.paypilot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    // A map to store OTPs temporarily for each user email
    private Map<String, OtpEntry> otpStorage = new HashMap<>();

    // OtpEntry class to store OTP and the timestamp of generation
    public class OtpEntry {
        private String otp;
        private long timestamp;

        public OtpEntry(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }

        public String getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
   
    
    // Generates and sends OTP to email
    public void generateAndSendOtp(String email) {
        String otp = generateOtp();
        long currentTime = System.currentTimeMillis();
        otpStorage.put(email, new OtpEntry(otp, currentTime));

        // Send OTP via email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Verification");
        message.setText("Your OTP is: " + otp + ". Please enter this OTP within 5 minutes.");
        mailSender.send(message);
    }

    // Utility to generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }


    // Verifies if the OTP is correct
    public boolean verifyOtp(String email, String otp) {
        OtpEntry otpEntry = otpStorage.get(email);
        if (otpEntry == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - otpEntry.getTimestamp();
        long fiveMinutesInMillis = 5 * 60 * 1000;

        // Check if the OTP is valid and within the 5-minute window
        return otpEntry.getOtp().equals(otp) && timeElapsed <= fiveMinutesInMillis;
    }

}
