package com.paypilot.dto;

import java.util.Date;

public class ScheduledPaymentDTO {
    private String paymentFrequency;
    private Date scheduledDate;
    private double amount;
    private String paymentMode;
    private String payerAccountNumber;
    private String status;
    private boolean scheduled;
    private PayeeDTO payee;
    private int billId;  // Only billId, not the entire Bill object
	public String getPaymentFrequency() {
		return paymentFrequency;
	}
	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPayerAccountNumber() {
		return payerAccountNumber;
	}
	public void setPayerAccountNumber(String payerAccountNumber) {
		this.payerAccountNumber = payerAccountNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isScheduled() {
		return scheduled;
	}
	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public PayeeDTO getPayee() {
		return payee;
	}
	public void setPayee(PayeeDTO payee) {
		this.payee = payee;
	}
	
}