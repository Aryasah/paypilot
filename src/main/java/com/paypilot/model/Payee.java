package com.paypilot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Payee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payeeId;

    @NotBlank(message = "Payee Name is required")
    @Size(min = 2, max = 100, message = "Payee Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String payeeName;

    @NotBlank(message = "Payee Address is required")
    @Size(min = 5, max = 255, message = "Payee Address must be between 5 and 255 characters")
    @Column(nullable = false)
    private String payeeAddress;

    @NotBlank(message = "Payee Bank Details are required")
    @Size(min = 5, max = 255, message = "Payee Bank Details must be between 5 and 255 characters")
    @Column(nullable = false)
    private String payeeBankDetails;

    // Constructor, Getters, Setters

    public Payee() {}

    public Payee(String payeeName, String payeeAddress, String payeeBankDetails) {
        this.payeeName = payeeName;
        this.payeeAddress = payeeAddress;
        this.payeeBankDetails = payeeBankDetails;
    }
    
    public Payee(Integer payeeId,String payeeName, String payeeAddress, String payeeBankDetails) {
    	this.payeeId=payeeId;
        this.payeeName = payeeName;
        this.payeeAddress = payeeAddress;
        this.payeeBankDetails = payeeBankDetails;
    }

    public Integer getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Integer payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getPayeeBankDetails() {
        return payeeBankDetails;
    }

    public void setPayeeBankDetails(String payeeBankDetails) {
        this.payeeBankDetails = payeeBankDetails;
    }

	@Override
	public String toString() {
		return "Payee [payeeId=" + payeeId + ", payeeName=" + payeeName + ", payeeAddress=" + payeeAddress
				+ ", payeeBankDetails=" + payeeBankDetails + "]";
	}

}
