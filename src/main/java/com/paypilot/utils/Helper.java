package com.paypilot.utils;

import java.util.Date;

import com.paypilot.dto.PaymentDTO;
import com.paypilot.dto.ScheduledPaymentDTO;
import com.paypilot.model.Bill;
import com.paypilot.model.Payee;
import com.paypilot.model.Payment;
import com.paypilot.model.ScheduledPayment;

public class Helper {
	public static void normalizePaymentFields(Payment payment) {
        if (payment.getCategory() != null) {
            payment.setCategory(payment.getCategory().toUpperCase());
        }
        if (payment.getPaymentMode() != null) {
            payment.setPaymentMode(payment.getPaymentMode().toUpperCase());
        }
        if (payment.getStatus() != null) {
            payment.setStatus(payment.getStatus().toUpperCase());
        }
        else {
        	payment.setStatus("PENDING");
        }
        if(payment.getPaymentDate() == null) {
        	payment.setPaymentDate(new Date());
        }
    }
	public static void normalizeScheduledPayment(ScheduledPayment scheduledPayment) {
	    if (scheduledPayment.getPaymentFrequency() != null) {
	        scheduledPayment.setPaymentFrequency(scheduledPayment.getPaymentFrequency().toUpperCase());
	    }
	    else {
	    	scheduledPayment.setPaymentFrequency("ONE_TIME");
	    }
	    if(scheduledPayment.getUpdateDate() == null) {
	    	scheduledPayment.setUpdateDate(new Date());
	    }
	    if(scheduledPayment.getPaymentMode() != null) {
	    	scheduledPayment.setPaymentMode(scheduledPayment.getPaymentMode().toUpperCase());
	    }
	    if (scheduledPayment.getStatus() != null) {
	        scheduledPayment.setStatus(scheduledPayment.getStatus().toUpperCase());
	    }
	    else {
	    	scheduledPayment.setStatus("SCHEDULED");
	    }
	}
	
	
	
	public static ScheduledPayment convertToEntity(ScheduledPaymentDTO scheduledPaymentDTO, Payee payee,Bill bill) {
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        scheduledPayment.setPaymentFrequency(scheduledPaymentDTO.getPaymentFrequency());
        scheduledPayment.setScheduledDate(scheduledPaymentDTO.getScheduledDate());
        scheduledPayment.setAmount(scheduledPaymentDTO.getAmount());
        scheduledPayment.setPaymentMode(scheduledPaymentDTO.getPaymentMode());
        scheduledPayment.setPayerAccountNumber(scheduledPaymentDTO.getPayerAccountNumber());
        scheduledPayment.setStatus(scheduledPaymentDTO.getStatus());
        scheduledPayment.setScheduled(scheduledPaymentDTO.isScheduled());
        scheduledPayment.setPayee(payee);
        scheduledPayment.setBill(bill);
        return scheduledPayment;
    }
	
	public static Payment convertToEntity(PaymentDTO paymentDTO,Bill bill) {
        Payment payment = new Payment();
        payment.setPayerAccountNumber(paymentDTO.getPayerAccountNumber());
        payment.setAmountPaid(paymentDTO.getAmountPaid());
        payment.setCategory(paymentDTO.getCategory());
        payment.setPaymentMode(paymentDTO.getPaymentMode());
        payment.setStatus(paymentDTO.getStatus());
        payment.setBill(bill);
        return payment;
    }
}
