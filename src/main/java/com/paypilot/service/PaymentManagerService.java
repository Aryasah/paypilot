package com.paypilot.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.paypilot.dto.BillWithAmountPaidDTO;
import com.paypilot.model.Bill;
import com.paypilot.model.Payment;
import com.paypilot.model.PaymentOverview;
import com.paypilot.model.ScheduledPayment;
import com.paypilot.repository.BillRepository;
import com.paypilot.repository.PaymentRepository;
import com.paypilot.repository.ScheduledPaymentRepository;
import jakarta.validation.Valid;

@Service
@Validated
public class PaymentManagerService {

	@Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;
    @Autowired
    private BillRepository billRepository;

    public ScheduledPayment schedulePayment(ScheduledPayment scheduledPayment) {
//    	scheduledPayment.schedulePayment();
        return scheduledPaymentRepository.save(scheduledPayment);
    }
    
    public List<ScheduledPayment> getScheduledPaymentsByBillId(Integer billId) {
        return scheduledPaymentRepository.findByBillBillId(billId);
    }

 // Method to cancel a scheduled payment using scheduledPaymentId
    public ScheduledPayment cancelScheduledPayment(Integer scheduledPaymentId) {
        Optional<ScheduledPayment> scheduledPaymentOptional = scheduledPaymentRepository.findByScheduledPaymentId(scheduledPaymentId);

        if (!scheduledPaymentOptional.isPresent()) {
            throw new RuntimeException("ScheduledPayment with ID: " + scheduledPaymentId + " not found.");
        }

        ScheduledPayment scheduledPayment = scheduledPaymentOptional.get();
        scheduledPayment.setScheduled(false);
        scheduledPayment.setStatus("CANCELLED");
        
        return scheduledPaymentRepository.save(scheduledPayment); 
    }
  
    // Get all scheduled payments
    public List<ScheduledPayment> getScheduledPayment() {
    	return scheduledPaymentRepository.findAll();
    }
    
    // Get all scheduled payments by userId
    public List<ScheduledPayment> getScheduledPaymentsByUserId(String userId) {
        return scheduledPaymentRepository.findScheduledPaymentsByUserId(userId);
    }

    // Method to modify a scheduled payment's date using scheduledPaymentId
    public ScheduledPayment modifyScheduledPayment(Integer scheduledPaymentId, Date newDate) {
        Optional<ScheduledPayment> scheduledPaymentOptional = scheduledPaymentRepository.findByScheduledPaymentId(scheduledPaymentId);

        if (!scheduledPaymentOptional.isPresent()) {
            throw new RuntimeException("ScheduledPayment with ID: " + scheduledPaymentId + " not found.");
        }

        ScheduledPayment scheduledPayment = scheduledPaymentOptional.get();
        scheduledPayment.setScheduledDate(newDate);
        scheduledPayment.setUpdateDate(new Date()); // setting current date as update date
        
        return scheduledPaymentRepository.save(scheduledPayment); 
    }

    public Payment savePayment(@Valid Payment payment) {
    	return paymentRepository.save(payment);
    }
    
    public List<Payment> getPaymentHistory(String userId, String category, Date fromDate, Date toDate) throws Exception {
        if (category == null || category.isEmpty() || category.equalsIgnoreCase("all")) {
            return paymentRepository.findAllPaymentsByDateRange(userId, fromDate, toDate);
        }
        return paymentRepository.findPaymentsByCategoryAndDateRange(userId, category.toUpperCase(), fromDate, toDate);
    }

    public List<Payment> getPaymentProgressByCategory(String userId, String category) throws Exception {
        String status = "PENDING";
        return paymentRepository.findByCategoryAndStatus(userId, category.toUpperCase(), status);
    }

    public List<Payment> getPaymentProgress(String userId) throws Exception {
        String status = "PENDING";
        return paymentRepository.findByStatus(userId, status);
    }

    public List<PaymentOverview> getPaymentOverview(String userId, String category, Date fromDate, Date toDate) {
        if (category == null || category.isEmpty() || category.equalsIgnoreCase(category)) {
            return paymentRepository.getPaymentOverview(userId, fromDate, toDate);
        }
        return paymentRepository.getPaymentOverview(userId, category.toUpperCase(), fromDate, toDate);
    }    
    
    
    public List<BillWithAmountPaidDTO> getBillsWithAmountPaid(String userId) {
        List<Bill> bills = billRepository.findBillsByUserId(userId);
        List<BillWithAmountPaidDTO> extendedBills = new ArrayList<>();

        for (Bill bill : bills) {
            // Sum payments with status 'COMPLETED'
            double paymentSum = paymentRepository.findByBillAndStatus(bill, "COMPLETED")
                                                 .stream()
                                                 .mapToDouble(Payment::getAmountPaid)
                                                 .sum();

            // Sum scheduled payments with status 'COMPLETED'
            double scheduledPaymentSum = scheduledPaymentRepository.findByBillAndStatus(bill, "COMPLETED")
                                                                    .stream()
                                                                    .mapToDouble(ScheduledPayment::getAmount)
                                                                    .sum();

            // Total amount paid
            double totalAmountPaid = paymentSum + scheduledPaymentSum;

            // Create DTO object
            BillWithAmountPaidDTO dto = new BillWithAmountPaidDTO(
                bill.getBillId(),
                bill.getBillName(),
                bill.getBillCategory(),
                bill.getDueDate(),
                bill.getAmount(),
                bill.getReminderFrequency(),
                bill.getAttachment(),
                bill.getNotes(),
                bill.getIsRecurring(),
                bill.getPaymentStatus(),
                bill.getUserId(),
                totalAmountPaid
            );

            extendedBills.add(dto);
        }

        return extendedBills;
    }
    
    
    
    
    // New method for fetching bills
    public List<Bill> getBillsByCategoryAndDateRange(String userId, String billCategory, Date fromDate, Date toDate) {
    	if(billCategory == null || billCategory.isBlank() || billCategory.equalsIgnoreCase("all")) {
    		if(fromDate == null || toDate == null) {
    			return billRepository.findBillsByUserId(userId);
    		}
    		return billRepository.findByDueDateBetweenAndUserId(fromDate, 
                    toDate,
                    userId);
    	}
    	if(fromDate == null || toDate == null) {
			return billRepository.findByBillCategoryAndUserId(billCategory,userId);
		}
    	
        return billRepository.findByBillCategoryAndDueDateBetweenAndUserId(
        		 billCategory, 
                 fromDate, 
                 toDate,
                 userId);
    }
    public List<Payment> getPaymentsByBillId(int billId) {
        return paymentRepository.findByBillBillId(billId);
    }
    
    // Method to update scheduled payments whose date is today
    public void processScheduledPayments() {
        Date today = new Date();
        List<ScheduledPayment> duePayments = scheduledPaymentRepository.findScheduledPaymentsDueToday(today);
        System.out.println(duePayments.size() + " Size of due payments");
        for (ScheduledPayment payment : duePayments) {
            payment.markAsCompleted();
            // Optionally update associated bill status to "Paid"
            Bill bill = payment.getBill();
            if (bill != null) {
            	double paymentSum = paymentRepository.findByBillAndStatus(bill, "COMPLETED")
                        .stream()
                        .mapToDouble(Payment::getAmountPaid)
                        .sum();

				// Sum scheduled payments with status 'COMPLETED'
				double scheduledPaymentSum = scheduledPaymentRepository.findByBillAndStatus(bill, "COMPLETED")
				                                           .stream()
				                                           .mapToDouble(ScheduledPayment::getAmount)
				                                           .sum();
				double totalAmountPaid = paymentSum + scheduledPaymentSum;
				BigDecimal amountDue = bill.getAmount().subtract(BigDecimal.valueOf(totalAmountPaid));
				if (amountDue.compareTo(BigDecimal.ZERO) <= 0) {
				    // amountDue is less than or equal to zero
					bill.setPaymentStatus("Paid");
					billRepository.save(bill);
				}
            }
            scheduledPaymentRepository.save(payment);
        }
    }
    
}
