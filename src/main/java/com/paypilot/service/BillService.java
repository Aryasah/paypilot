package com.paypilot.service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypilot.model.Bill;
import com.paypilot.repository.BillRepository;

/**
 *
 * Description: The `BillService` class provides business logic for managing
 * bills in the application. It interacts with the `BillRepository` to perform
 * various operations
 * 
 * The class includes the following methods:
 * 
 * Author: Varsha Veeraraghavan 
 * Date: 30/8/2024 
 * 
 * Methods written:
 * - `getUpcomingBillsService`:Retrieves a list of upcoming unpaid bills due after the current date. 
 * - `snoozeBillService`: Updates the due date of a specific bill to snooze its reminder. 
 * - `markBillAsPaidService`: Marks a specific bill as paid by updating its payment status. 
 * - `getBillsOverview`: Retrieves a list of bills based on category, date range, and payment status.
 * 
 * 
 * Author: Tanya Arora
 * Date: 31/08/2024 
 * Methods written:
 * - `findByIdService`: Retrieves a bill by its ID using repository.findById(id). Returns the bill if found, otherwise returns null.
 * - `getAllBillsService`: Retrieves all bills from the repository using repository.findAll().
 * - `addBillService`: Saves a new bill to the repository using repository.save(bill).
 * - `removeBillService`: Removes a bill by its ID. Checks if the bill exists before attempting to delete it.
 * - `getOverdueBillsService`: Retrieves a list of overdue bills that are past their due date and have not been marked as paid.
 */
@Service
public class BillService {

	@Autowired
	private BillRepository repository;

	
    /**
     * Retrieves a bill by its ID.
     * 
     * @param id The ID of the bill to retrieve.
     * @return The bill with the specified ID, or null if not found.
     */
    public Bill findByIdService(int id , String userId) {
    	Optional<Bill> bill = repository.findByBillIdAndUserId(id, userId);
        return bill.orElse(null);
    }

    /**
     * Retrieves all bills in the repository based on user_id
     * 
     * @return A list of all bills.
     */
    public List<Bill> getAllBillsService(String userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Adds a new bill to the repository.
     * 
     * @param bill The bill to add.
     * @return The added bill.
     */
    public Bill addBillService(Bill bill) {
        return repository.save(bill);
    }

    /**
     * Removes a bill from the repository by its ID.
     * 
     * @param id The ID of the bill to remove.
     * @return true if the bill was successfully removed, false if not found.
     */
    public boolean removeBillService(int id, String userId) {
        Optional<Bill> bill = repository.findByBillIdAndUserId(id, userId);
        if (bill.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of overdue bills that are past their due date and
     * have not been marked as paid.
     * 
     * @return A list of overdue bills with an "Unpaid" status.
     */
    public List<Bill> getOverdueBillsService(String userId) {
        Date today = new Date();
        return repository.findByDueDateBeforeAndPaymentStatusAndUserId(today, "Unpaid", userId);
    }

    
	/**
	 * Retrieves a list of upcoming bills that are due after the current date and
	 * have an "Unpaid" status.
	 * 
	 * @return A list of unpaid bills with due dates after today's date.
	 */

    public List<Bill> getUpcomingBillsService(String userId) {
        Date today = new Date();
        return repository.findByDueDateAfterAndPaymentStatusAndUserId(today, "Unpaid", userId);
    }


	/**
	 * Snoozes a bill by updating its due date to a new specified date.
	 * 
	 * @param bill    The bill to be snoozed.
	 * @param newDate The new due date for the bill.
	 * @return true if the bill was successfully updated, false if the bill was not
	 *         found.
	 */

    public boolean snoozeBillService(Bill bill, Date newDate, String userId) {
        Optional<Bill> existingBill = repository.findByBillIdAndUserId(bill.getBillId(), userId);
        if (existingBill.isPresent()) {
            Bill billToUpdate = existingBill.get();
            billToUpdate.setDueDate(newDate);
            repository.save(billToUpdate);
            return true;
        }
        return false;
    }

	/**
	 * Marks a bill as paid by updating its payment status to "paid".
	 * 
	 * @param bill The bill to be marked as paid.
	 * @return true if the bill was successfully marked as paid, false if the bill
	 *         was not found.
	 */
    public boolean markBillAsPaidService(Bill bill, String userId) {
        Optional<Bill> existingBill = repository.findByBillIdAndUserId(bill.getBillId(), userId);
        if (existingBill.isPresent()) {
            Bill billToUpdate = existingBill.get();
            billToUpdate.setPaymentStatus("Paid");
            repository.save(billToUpdate);
            return true;
        }
        return false;
    }


	/**
	 * Retrieves an overview of bills filtered by category, due date range, and
	 * payment status.
	 * 
	 * @param category The category of bills to filter by.
	 * @param fromDate The start date of the due date range to filter by.
	 * @param toDate   The end date of the due date range to filter by.
	 * @param status   The payment status of the bills to filter by.
	 * @param userId	user id 
	 * @return A list of bills matching the specified criteria.
	 */
    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status, String userId) {
        return repository.findByBillCategoryAndDueDateBetweenAndPaymentStatusAndUserId(category, fromDate, toDate, status, userId);
    }
	
	
	public List<Bill> getBillsByUserIdAndDateRange(String userId, LocalDate fromDate, LocalDate toDate) {
	    List<Bill> bills =  repository.findByUserIdAndDueDateBetween(userId, fromDate, toDate);
		return bills;
	}
	public List<Bill> getBillsByUserIdCategoryAndDateRange(String userId, String billCategory, LocalDate fromDate, LocalDate toDate) {
	    return repository.findByUserIdAndCategoryAndDueDateBetween(userId, billCategory, fromDate, toDate);
	}
	public List<Bill> getBillsByUserIdAndCategory(String userId, String billCategory) {
	    List<Bill> bills = repository.findByUserIdAndCategory(userId, billCategory);
	    System.out.println("Bills fetched: " + bills);
	    return bills;
	}

	public Bill findByBillId(int id) {
    	Optional<Bill> bill = repository.findByBillId(id);
        return bill.orElse(null);
    }
}
