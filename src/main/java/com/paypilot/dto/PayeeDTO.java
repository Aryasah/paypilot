package com.paypilot.dto;


public class PayeeDTO {
    private int payeeId;
    private String payeeName;
    private String payeeAddress;
    private String payeeBankDetails;
	public int getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(int payeeId) {
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

}
