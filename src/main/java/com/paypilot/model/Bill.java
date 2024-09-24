package com.paypilot.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

/**
 * Represents a bill with details including ID, name, category, due date,
 * amount, and more. Provides methods to manage bills.
 * 
 * Author: Subhra Prakash Dehury , Annotations added By Varsha Veeraraghavan
 * Date: 20/08/2024
 */

@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_seq")
    @SequenceGenerator(name = "bill_seq", sequenceName = "bill_SEQ", allocationSize = 1)
	
	
	
	/*
	 * 
	 * please create this sequence
	 * CREATE SEQUENCE BILL_SEQ
	   START WITH 1
	   INCREMENT BY 1;
	 */
	private Integer billId;

	@Column(name = "bill_name", nullable = false, length = 100)
	private String billName;

	@Column(name = "bill_category", nullable = false, length = 50)
	private String billCategory;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "reminder_frequency", length = 50)
	private String reminderFrequency;

	@Column(name = "attachment")
	private String attachment;

	@Column(name = "notes", length = 500)
	private String notes;

	@Column(name = "is_recurring")
	private char isRecurring;

	@Column(name = "payment_status", length = 20)
	private String paymentStatus;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	 
	/**
	 * Constructor to initialize a Bill object with all necessary details.
	 *
	 * @param billId            Unique identifier for the bill
	 * @param billName          Name of the bill
	 * @param billCategory      Category of the bill
	 * @param dueDate           Due date for the bill payment
	 * @param amount            Amount to be paid for the bill
	 * @param reminderFrequency Frequency of reminders for the bill
	 * @param attachment        Attachment related to the bill
	 * @param notes             Additional notes or comments about the bill
	 * @param isRecurring       Indicates if the bill is recurring - y or n
	 * @param paymentStatus     Status of the bill payment
	 * @param userId			To store the user ID
	 */

	public Bill() {
	}


	public Bill(Integer billId, String billName, String billCategory, Date dueDate, BigDecimal amount,
			String reminderFrequency, String attachment, String notes, char isRecurring, String paymentStatus,
			String userId) {
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

	}


	// Getters and Setters

	public int getBillId() {
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
}
