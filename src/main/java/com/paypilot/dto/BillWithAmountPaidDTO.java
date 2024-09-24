package com.paypilot.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BillWithAmountPaidDTO {

    private Integer billId;
    private String billName;
    private String billCategory;
    private Date dueDate;
    private BigDecimal amount;
    private String reminderFrequency;
    private String attachment;
    private String notes;
    private char isRecurring;
    private String paymentStatus;
    private String userId;
    private double amountPaid; // Additional field

    // Getters and Setters
    public BillWithAmountPaidDTO(Integer billId, String billName, String billCategory, Date dueDate, BigDecimal amount, 
                                 String reminderFrequency, String attachment, String notes, char isRecurring, 
                                 String paymentStatus, String userId, double amountPaid) {
        this.billId = billId;
        this.billName = billName;
        this.billCategory = billCategory;
        this.dueDate = dueDate;
        this.amount = amount;
        this.reminderFrequency = reminderFrequency;
        this.attachment = attachment;
        this.notes = notes;
        this.isRecurring = isRecurring;
        this.paymentStatus = paymentStatus;
        this.userId = userId;
        this.amountPaid = amountPaid;
    }

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getBillCategory() {
		return billCategory;
	}

	public void setBillCategory(String billCategory) {
		this.billCategory = billCategory;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getReminderFrequency() {
		return reminderFrequency;
	}

	public void setReminderFrequency(String reminderFrequency) {
		this.reminderFrequency = reminderFrequency;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public char getIsRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(char isRecurring) {
		this.isRecurring = isRecurring;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
}
