/**
 * Author: Shrilakshmi Upadhya
 * Date: 10th September 2024
 * 
 * Description: This is the dto for verifying users in case of ForgotPassword flow.
 */

package com.paypilot.dto;

public class UserVerificationRequest {
	private String email;
    private String userId;
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}

