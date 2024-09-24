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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;


@Entity
@Table(name = "payment")
public class Payment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Payment date cannot be null")
    private Date paymentDate;

    @NotNull(message = "Payment mode cannot be null")
    @Pattern(regexp = "CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER|PAYPAL", message = "Invalid payment mode as of now we are supporting CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER|PAYPAL as mode of payment")
    private String paymentMode;

    @NotNull(message = "Payer account number cannot be null")
    @Pattern(regexp = "^\\d{6,}$", message = "Payer account number must be at least 6 digits")
    private String payerAccountNumber;

    @DecimalMin(value = "0.0", inclusive = true, message = "Amount paid must be positive")
    private double amountPaid;

    @NotNull(message = "Status cannot be null")
    @Pattern(regexp = "PENDING|COMPLETED|FAILED|CANCELLED", message = "Invalid payment status available options are PENDING|COMPLETED|FAILED|CANCELLED")
    private String status;

    @NotNull(message = "Category cannot be null")
    @Pattern(regexp = "UTILITIES|GROCERIES|ENTERTAINMENT|HEALTH", message = "Invalid payment category available options are UTILITIES|GROCERIES|ENTERTAINMENT|HEALTH")
    private String category;
    
    // ManyToOne relationship with Bill
    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "billId", nullable = false)
    private Bill bill;

    public Payment(int paymentId, Date paymentDate, String paymentMode, String payerAccountNumber,
                   double amountPaid, String status, String category,Bill bill) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.payerAccountNumber = payerAccountNumber;
        this.amountPaid = amountPaid;
        this.status = status;
        this.category = category;
        this.bill = bill;
    }

    public Payment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void makePayment() {
    }

    public void cancelPayment() {
    }

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentDate=" + paymentDate + ", paymentMode=" + paymentMode
				+ ", payerAccountNumber=" + payerAccountNumber + ", amountPaid=" + amountPaid + ", status=" + status
				+ ", category=" + category + ", bill=" + bill + "]";
	}

	

}
