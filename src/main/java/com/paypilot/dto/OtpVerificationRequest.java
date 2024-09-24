/**
 * Author: Shrilakshmi Upadhya
 * Date: 6th September 2024
 * 
 * Description: This is the dto for managing email ids and corresponding OTPs.
 */

package com.paypilot.dto;

public class OtpVerificationRequest {
    private String email;
    private String otp;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

