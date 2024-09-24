package com.paypilot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypilot.dto.BillWithAmountPaidDTO;
import com.paypilot.dto.PaymentDTO;
import com.paypilot.model.Bill;
import com.paypilot.model.Payment;
import com.paypilot.model.PaymentOverview;
import com.paypilot.model.ScheduledPayment;
import com.paypilot.service.BillService;
import com.paypilot.service.PaymentManagerService;
import com.paypilot.utils.Helper;
import com.paypilot.utils.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
	private final PaymentManagerService paymentManagerService;
	private final BillService billService;
	private final Validator validator;

	@Autowired
	public PaymentController(PaymentManagerService paymentManagerService, BillService billService, Validator validator) {
	    this.paymentManagerService = paymentManagerService;
	    this.billService = billService;
	    this.validator = validator;
	}

	// Endpoint to add a new payment
	@PostMapping("/add")
	public ResponseEntity<?> addPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
	    // Fetch the bill associated with the payment
	    Bill bill = billService.findByBillId(paymentDTO.getBillId());
	    System.out.println("billId " + paymentDTO.getBillId());
	    if (bill == null) {
	        // Return an error response if the bill does not exist
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bill with billId " 
	                + paymentDTO.getBillId() + " does not exist.");
	    }
	    // Convert DTO to entity and normalize payment fields
	    Payment payment = Helper.convertToEntity(paymentDTO, bill);    
	    Helper.normalizePaymentFields(payment);
	    System.out.println(payment);
	    // Validate the payment object
	    ResponseEntity<?> validationErrorResponse = ValidationUtil.validateEntity(payment, validator);
	    if (validationErrorResponse != null) {
	        return validationErrorResponse; // Return validation errors if any
	    }
	    // Save the payment and return the saved payment object
	    Payment savedPayment = paymentManagerService.savePayment(payment);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
	}

	// Endpoint to retrieve payment history based on optional userId, category, and date range
	@GetMapping("/history")
	public ResponseEntity<List<Payment>> getPaymentHistory(
	        @RequestParam(required = false) String userId,
	        @RequestParam(required = false) String category,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws Exception {
	    // Fetch payment history from the service
	    List<Payment> payments = paymentManagerService.getPaymentHistory(userId, category, fromDate, toDate);
	    return ResponseEntity.ok(payments);
	}

	// Endpoint to retrieve payment overview based on optional userId, category, and date range
	@GetMapping("/overview")
	public ResponseEntity<List<PaymentOverview>> getPaymentOverview(
	        @RequestParam(required = false) String userId,
	        @RequestParam(required = false) String category,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    // Fetch payment overview from the service
	    List<PaymentOverview> overview = paymentManagerService.getPaymentOverview(userId, category, fromDate, toDate);
	    return ResponseEntity.ok(overview);
	}

	// Endpoint to retrieve payment progress based on optional userId and category
	@GetMapping("/progress")
	public ResponseEntity<List<Payment>> getPaymentProgress(
	        @RequestParam(required = false) String userId,
	        @RequestParam(required = false) String category) throws Exception {
	    List<Payment> payments;
	    if (category == null || category.isEmpty() || category.equalsIgnoreCase("all")) {
	        // Fetch all payments if no category is provided
	        payments = paymentManagerService.getPaymentProgress(userId);
	    } else {
	        // Fetch payments by category if a category is provided
	        payments = paymentManagerService.getPaymentProgressByCategory(userId, category.toUpperCase());
	    }
	    return ResponseEntity.ok(payments);
	}

	// Endpoint to retrieve payments by billId
	@GetMapping("/byBillId")
	public ResponseEntity<List<Payment>> getPaymentsByBillId(
	        @RequestParam int billId) {
	    // Fetch payments associated with the given billId
	    List<Payment> payments = paymentManagerService.getPaymentsByBillId(billId);
	    return ResponseEntity.ok(payments);
	}
	
	
    // New  for fetching bill overview
    @GetMapping("/overview/bill")
    public ResponseEntity<List<Bill>> getBills(
            @RequestParam(required=false) String billCategory,
            @RequestParam String userId,
            @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        List<Bill> bills = paymentManagerService.getBillsByCategoryAndDateRange(userId, billCategory, fromDate, toDate);
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/with-amount-paid")
    public ResponseEntity<List<BillWithAmountPaidDTO>> getBillsWithAmountPaid(
    		 @RequestParam String userId
    		) {
        List<BillWithAmountPaidDTO> billsWithAmountPaid = paymentManagerService.getBillsWithAmountPaid(userId);
        return ResponseEntity.ok(billsWithAmountPaid);
    }
}
