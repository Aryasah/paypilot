package com.paypilot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "pan_details")
    private String pan;

    @Column(name = "bank_account_number")
    private String bankAccount;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "banking_partner")
    private String bankName;

    public User() {}

    public User(String userId, String email, String password, String pan, String bankAccount, String ifscCode, String bankName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.pan = pan;
        this.bankAccount = bankAccount;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
