package com.paypilot.controller;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypilot.model.Bill;
import com.paypilot.service.BillService;

import jakarta.servlet.http.HttpServletRequest;


/**
 * 
 * Description: 
 * This class is a REST controller for managing bills in the
 * application. It provides endpoints for: 
 * 
 * 
 * Author:  Varsha Veeraraghavan 
 * Date: 30/8/2024
 * Methods Written:
 * - Retrieving upcoming bills (`getUpcomingBills`) 
 * - Snoozing bill reminders (`snoozeBill`) 
 * - Marking bills as paid (`markBillAsPaid`) 
 * - Getting an overview of bills based on category, date range, and status (`getBillsOverview`)
 * 
 * 
 * Author:
 * Date: 31/8/2024
 * Methods Written:Tanya Arora
 * - Retrieving all bills (`getAllBills`)
 * - Retrieving a bill by id (`getBillById`)
 * - Adding a new bill (`addBill`)
 * - Removing a bill by ID (`removeBill`)
 * - Retrieving overdue bills (`getOverdueBills`)
 */
@RestController
@RequestMapping("/bill")
@CrossOrigin(origins = {"http://localhost:3000", "http://papilot.s3-website.ap-south-1.amazonaws.com"})
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * Retrieves a list of upcoming bills that are due soon.
     * 
     * @return a list of Bill objects representing the upcoming bills.
     */
    @GetMapping("/upcoming")
    public List<Bill> getUpcomingBills(@RequestParam String userId) {
        return billService.getUpcomingBillsService(userId);
    }

    /**
     * Snoozes the reminder for a specific bill, delaying its due date.
     * 
     * @param bill    The bill to be snoozed, provided in the request body.
     * @param newDate The new due date for the bill, provided as a request
     *                parameter.
     * @return true if the snooze operation was successful, false otherwise.
     */
    @PostMapping("/snooze")
    public boolean snoozeBill(@RequestBody Bill bill, 
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date newDate, 
                              @RequestParam String userId) {
        return billService.snoozeBillService(bill, newDate, userId);
    }

    /**
     * Marks a specific bill as paid.
     * 
     * @param bill The bill to be marked as paid, provided in the request body.
     * @return true if the bill was successfully marked as paid, false otherwise.
     */
    @PostMapping("/markAsPaid")
    public boolean markBillAsPaid(@RequestBody Bill bill, @RequestParam String userId) {
        return billService.markBillAsPaidService(bill, userId);
    }

    /**
     * Retrieves an overview of bills filtered by category, date range, and payment
     * status.
     * 
     * @param category The category of bills to filter by.
     * @param fromDate The start date of the date range to filter by.
     * @param toDate   The end date of the date range to filter by.
     * @param status   The payment status to filter by (e.g., "paid", "unpaid").
     * @return a list of Bill objects matching the specified criteria.
     */
    @GetMapping("/overview")
    public List<Bill> getBillsOverview(@RequestParam String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam String status, @RequestParam String userId) {
    	return billService.getBillsOverview(category, fromDate, toDate, status, userId);
    }

    /**
     * Retrieves a bill by its ID.
     * 
     * @param id The ID of the bill to retrieve.
     * @return The Bill object with the specified ID, or null if not found.
     */
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable int id, @RequestParam String userId) {
        return billService.findByIdService(id, userId);
    }

    /**
     * Retrieves a list of all bills.
     * 
     * @return A list of all Bill objects.
     */
    @GetMapping("/all")
    public List<Bill> getAllBills(@RequestParam String userId) {
        return billService.getAllBillsService(userId);
    }

    /**
     * Adds a new bill to the repository.
     * 
     * @param bill The Bill object to add.
     * @return The added Bill object.
     */
    @PostMapping("/add")
    public Bill addBill(@RequestBody Bill bill) {
        return billService.addBillService(bill);
    }

    /**
     * Removes a bill by its ID.
     * 
     * @param id The ID of the bill to remove.
     * @return true if the bill was successfully removed, false if the bill was not found.
     */
    @DeleteMapping("/remove/{id}")
    public boolean removeBill(@PathVariable int id, @RequestParam String userId) {
        return billService.removeBillService(id, userId);
    }

    /**
     * Retrieves a list of overdue bills.
     * 
     * @return A list of overdue Bill objects.
     */
    @GetMapping("/overdue")
    public List<Bill> getOverdueBills(@RequestParam String userId) {
        return billService.getOverdueBillsService(userId);
    }
    
    /**
     * Get Bills By userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bill>> getBillsByUserId(@PathVariable String userId) {
        List<Bill> bills = billService.getAllBillsService(userId);
        if (bills.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bills);
    }
    
    
    @GetMapping("/user/{userId}/category/{billCategory}")
    public ResponseEntity<List<Bill>> getBillsByUserIdCategoryAndOptionalDateRange(
            @PathVariable String userId, 
            @PathVariable String billCategory,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            HttpServletRequest request) {

        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Query String: " + request.getQueryString());

        List<Bill> bills;

        if ("All".equalsIgnoreCase(billCategory)) {
            // Fetch all bills for the user, with or without the date range
            if (fromDate != null && toDate != null) {
                bills = billService.getBillsByUserIdAndDateRange(userId, fromDate, toDate);
            } else {
                bills = billService.getAllBillsService(userId);
            }
        } else {
            // Fetch bills by user and category, with or without the date range
            if (fromDate != null && toDate != null) {
                bills = billService.getBillsByUserIdCategoryAndDateRange(userId, billCategory, fromDate, toDate);
            } else {
                bills = billService.getBillsByUserIdAndCategory(userId, billCategory);
            }
        }

        if (bills.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bills);
    }
}
