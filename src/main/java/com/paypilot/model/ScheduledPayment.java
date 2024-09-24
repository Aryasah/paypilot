package com.paypilot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

@Entity
@Table(name = "scheduled_payments")
public class ScheduledPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduledPaymentId;

    @NotNull(message = "Payment frequency cannot be null")
    @Pattern(regexp = "ONE_TIME|DAILY|WEEKLY|MONTHLY|QUARTERLY|HALF_YEARLY|ANNUAL", message = "Payment frequency must be one of DAILY, WEEKLY, MONTHLY, QUARTERLY, HALF_YEARLY, or ANNUAL")
    private String paymentFrequency;

    @ManyToOne
    @JoinColumn(name = "payee_id", nullable = false)
    private Payee payee;

    @Temporal(TemporalType.DATE)
//    @FutureOrPresent(message = "Scheduled date must be today or in the future")
    @NotNull(message = "Scheduled date cannot be null")
    private Date scheduledDate;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be a positive value")
    private double amount;

    @NotNull(message = "Payment mode cannot be null")
    @Pattern(regexp = "CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER|PAYPAL", message = "Invalid payment mode as of now we are supporting CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER|PAYPAL as mode of payment")
    private String paymentMode;

    @NotBlank(message = "Payer account number cannot be blank")
    private String payerAccountNumber;

    private boolean isScheduled;

    @NotNull(message = "Status cannot be null")
    @Pattern(regexp = "SCHEDULED|COMPLETED|FAILED|CANCELLED", message = "Status must be one of SCHEDULED, COMPLETED, FAILED, or CANCELLED")
    private String status;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Update date cannot be null")
    private Date updateDate;
    
    // ManyToOne relationship with Bill
    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "billId", nullable = false)
    private Bill bill;

    public ScheduledPayment() {
        super();
    }

    public ScheduledPayment(Payee payee, String paymentFrequency, Date scheduledDate, double amount,
            String paymentMode, String payerAccountNumber, boolean isScheduled, String status, Date updateDate,Bill bill) {
		this.payee = payee;
		this.paymentFrequency = paymentFrequency;
		this.scheduledDate = scheduledDate;
		this.amount = amount;
		this.paymentMode = paymentMode;
		this.payerAccountNumber = payerAccountNumber;
		this.isScheduled = isScheduled;
		this.status = status;
		this.updateDate = updateDate;
		this.bill = bill;
		}

    
    public int getScheduledPaymentId() {
		return scheduledPaymentId;
	}

	public void setScheduledPaymentId(int scheduledPaymentId) {
		this.scheduledPaymentId = scheduledPaymentId;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public Payee getPayee() {
		return payee;
	}

	public void setPayee(Payee payee) {
		this.payee = payee;
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

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void schedulePayment() {
        this.isScheduled = true;
        this.status = "Scheduled";
        this.scheduledDate = new Date();
        this.updateDate = new Date();
    }

    public void modifyScheduledPayment(Date newDate) {
        this.scheduledDate = newDate;
        this.updateDate = newDate;
    }

    public void cancelScheduledPayment() {
        this.isScheduled = false;
        this.status = "Cancelled";
        this.updateDate = new Date();
    }

    public void markAsCompleted() {
        this.status = "COMPLETED";
        this.isScheduled = false;
        this.updateDate = new Date();
    }
    
	@Override
	public String toString() {
		return "ScheduledPayment [scheduledPaymentId=" + scheduledPaymentId + ", paymentFrequency=" + paymentFrequency
				+ ", payee=" + payee + ", scheduledDate=" + scheduledDate + ", amount=" + amount + ", paymentMode="
				+ paymentMode + ", payerAccountNumber=" + payerAccountNumber + ", isScheduled=" + isScheduled
				+ ", status=" + status + ", updateDate=" + updateDate + ", bill=" + bill + "]";
	}
    
}
