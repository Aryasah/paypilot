/**
 * Author: Shrilakshmi Upadhya
 * Date: 10th September 2024
 * 
 * Description: This is the dto for resetting password in case of ForgotPassword flow.
 */

package com.paypilot.dto;

public class PasswordResetRequest {
    private String email;
    private String newPassword;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

