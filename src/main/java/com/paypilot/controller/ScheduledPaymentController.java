package com.paypilot.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypilot.dto.PayeeDTO;
import com.paypilot.dto.ScheduledPaymentDTO;
import com.paypilot.model.Bill;
import com.paypilot.model.Payee;
import com.paypilot.model.ScheduledPayment;
import com.paypilot.service.BillService;
import com.paypilot.service.PayeeService;
import com.paypilot.service.PaymentManagerService;
import com.paypilot.utils.Helper;
import com.paypilot.utils.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/scheduled_payment")
@CrossOrigin(origins = {"http://localhost:3000", "http://papilot.s3-website.ap-south-1.amazonaws.com"})
public class ScheduledPaymentController {

	private PaymentManagerService paymentManagerService;
	private PayeeService payeeService;
	private BillService billService;
	private final Validator validator;
	@Autowired
    public ScheduledPaymentController(PaymentManagerService paymentManagerService,PayeeService payeeService,BillService billService, Validator validator) {
        this.paymentManagerService = paymentManagerService;
        this.payeeService = payeeService;
        this.billService = billService;
        this.validator = validator;
    }
	@PostMapping("/schedule")
	public ResponseEntity<?> schedulePayment(@Valid @RequestBody ScheduledPaymentDTO scheduledPaymentDTO) {
	    // Check if the Payee exists in the database
	    Optional<Payee> existingPayee = payeeService.getPayeeById(scheduledPaymentDTO.getPayee().getPayeeId());
	    if (!existingPayee.isPresent()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payee with ID " 
	            + scheduledPaymentDTO.getPayee().getPayeeId() + " does not exist.");
	    }

	    // Verify if the Payee details match the details in the database
	    Payee payeeFromDb = existingPayee.get();
	    PayeeDTO payeeFromRequest = scheduledPaymentDTO.getPayee();
	    
	    boolean payeeDetailsMatch = payeeFromDb.getPayeeName().equals(payeeFromRequest.getPayeeName()) &&
	                                payeeFromDb.getPayeeAddress().equals(payeeFromRequest.getPayeeAddress()) &&
	                                payeeFromDb.getPayeeBankDetails().equals(payeeFromRequest.getPayeeBankDetails());

	    if (!payeeDetailsMatch) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                             .body("Payee details do not match the existing payee with ID " 
	                             + scheduledPaymentDTO.getPayee().getPayeeId());
	    }
	    Bill bill = billService.findByBillId(scheduledPaymentDTO.getBillId());
	    System.out.println("billId "+ scheduledPaymentDTO.getBillId());
	    if(bill == null) {
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bill with billId " 
		            + scheduledPaymentDTO.getBillId() + " does not exist.");
	    }
	    ScheduledPayment scheduledPayment = Helper.convertToEntity(scheduledPaymentDTO, payeeFromDb, bill);
	    
	    // Normalize the fields before validation
	    Helper.normalizeScheduledPayment(scheduledPayment);

	    // Validate the ScheduledPayment object
	    ResponseEntity<?> validationErrorResponse = ValidationUtil.validateEntity(scheduledPayment, validator);
	    if (validationErrorResponse != null) {
	        return validationErrorResponse; // If there are validation errors, return the response
	    }
	    
	    // Proceed with scheduling the payment if validation passes and payee details match
	    ScheduledPayment modifiedScheduledPayment = paymentManagerService.schedulePayment(scheduledPayment);
	    return ResponseEntity.status(HttpStatus.CREATED).body(modifiedScheduledPayment);
	}


	// EndPoint to fetch scheduled payments by billId
    @GetMapping("/bill/{billId}")
    public ResponseEntity<?> getScheduledPaymentsByBillId(@PathVariable Integer billId) {
        List<ScheduledPayment> scheduledPayments = paymentManagerService.getScheduledPaymentsByBillId(billId);
        if (scheduledPayments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No scheduled payments found for billId: " + billId);
        }
        return ResponseEntity.ok(scheduledPayments);
    }
    
    // Cancel scheduled payment using scheduledPaymentId
    @GetMapping("/cancel/{scheduledPaymentId}")
    public ResponseEntity<ScheduledPayment> cancelScheduledPayment(@PathVariable Integer scheduledPaymentId) {
        ScheduledPayment cancelledScheduledPayment = paymentManagerService.cancelScheduledPayment(scheduledPaymentId);
        return ResponseEntity.status(HttpStatus.OK).body(cancelledScheduledPayment);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ScheduledPayment>> getScheduledPayments(@RequestParam(required = false) String userId) {
        List<ScheduledPayment> scheduledPayments;
        System.out.println("User Id Received "+userId);
        if (userId != null && !userId.isBlank()) {
            scheduledPayments = paymentManagerService.getScheduledPaymentsByUserId(userId);
            if (scheduledPayments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(scheduledPayments);
            }
        } else {
            scheduledPayments = paymentManagerService.getScheduledPayment();
        }
        return ResponseEntity.ok(scheduledPayments);
    }

    // Modify scheduled payment date using scheduledPaymentId
    @GetMapping("/modify/{scheduledPaymentId}")
    public ResponseEntity<ScheduledPayment> modifyScheduledPayment(
            @PathVariable Integer scheduledPaymentId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date newDate) {
        ScheduledPayment modifiedScheduledPayment = paymentManagerService.modifyScheduledPayment(scheduledPaymentId, newDate);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedScheduledPayment);
    }
}
