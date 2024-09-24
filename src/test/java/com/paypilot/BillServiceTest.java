package com.paypilot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.paypilot.model.Bill;
import com.paypilot.repository.BillRepository;
import com.paypilot.service.BillService;

/**
 * Description: Test class for the `BillService` class.
 * This class contains test cases for various methods in the `BillService` class.
 * It uses Mockito to mock the `BillRepository` and validate the service methods.
 * 
 * Authors: 
 * - Sonakshi Satpathy
 * - Subhra Prakash Dehury
 * 
 * Test Cases:
 * 
 * - Sonakshi Satpathy:
 *   - testFindByIdService(): Retrieves bill by ID.
 *   - testGetAllBillsService(): Retrieves all bills.
 *   - testAddBillService(): Adds a new bill.
 *   - testRemoveBillService(): Removes bill by ID.
 *   - testRemoveBillService_NotFound(): Handles non-existent bill removal.
 * 
 * - Subhra Prakash Dehury:
 *   - testGetOverdueBillsService(): Retrieves overdue bills.
 *   - testGetUpcomingBillsService(): Retrieves upcoming bills.
 *   - testSnoozeBillService(): Updates bill’s due date.
 *   - testMarkBillAsPaidService(): Marks bill as paid.
 *   - testGetBillsOverview(): Retrieves bills overview.
 */
class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillService billService;

    private final String userId = "user123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdService() {
        Bill mockBill = new Bill();
        mockBill.setBillId(1);
        Mockito.when(billRepository.findByBillIdAndUserId(1, userId)).thenReturn(Optional.of(mockBill));

        Bill foundBill = billService.findByIdService(1, userId);

        assertNotNull(foundBill);
        assertEquals(1, foundBill.getBillId());
    }

    @Test
    void testGetAllBillsService() {
        List<Bill> mockBills = List.of(new Bill(), new Bill());
        Mockito.when(billRepository.findByUserId(userId)).thenReturn(mockBills);

        List<Bill> bills = billService.getAllBillsService(userId);

        assertNotNull(bills);
        assertEquals(2, bills.size());
    }

    @Test
    void testAddBillService() {
        Bill newBill = new Bill();
        Mockito.when(billRepository.save(any(Bill.class))).thenReturn(newBill);

        Bill savedBill = billService.addBillService(newBill);

        assertNotNull(savedBill);
    }

    @Test
    void testRemoveBillService() {
        Bill mockBill = new Bill();
        mockBill.setBillId(1);
        Mockito.when(billRepository.findByBillIdAndUserId(1, userId)).thenReturn(Optional.of(mockBill));
        Mockito.doNothing().when(billRepository).deleteById(1);

        boolean isRemoved = billService.removeBillService(1, userId);

        assertTrue(isRemoved);
    }

    @Test
    void testRemoveBillService_NotFound() {
        Mockito.when(billRepository.findByBillIdAndUserId(1, userId)).thenReturn(Optional.empty());

        boolean isRemoved = billService.removeBillService(1, userId);

        assertFalse(isRemoved);
    }

    @Test
    void testGetOverdueBillsService() {
        List<Bill> mockBills = List.of(new Bill(), new Bill());
        Mockito.when(billRepository.findByDueDateBeforeAndPaymentStatusAndUserId(any(Date.class), eq("Unpaid"), eq(userId)))
               .thenReturn(mockBills);

        List<Bill> overdueBills = billService.getOverdueBillsService(userId);

        assertNotNull(overdueBills);
        assertEquals(2, overdueBills.size());
    }

    @Test
    void testGetUpcomingBillsService() {
        List<Bill> mockBills = List.of(new Bill(), new Bill());
        Mockito.when(billRepository.findByDueDateAfterAndPaymentStatusAndUserId(any(Date.class), eq("Unpaid"), eq(userId)))
               .thenReturn(mockBills);

        List<Bill> upcomingBills = billService.getUpcomingBillsService(userId);

        assertNotNull(upcomingBills);
        assertEquals(2, upcomingBills.size());
    }

    @Test
    void testSnoozeBillService() {
        Bill mockBill = new Bill();
        mockBill.setBillId(1);
        Date newDate = new Date();
        Mockito.when(billRepository.findByBillIdAndUserId(1, userId)).thenReturn(Optional.of(mockBill));
        Mockito.when(billRepository.save(any(Bill.class))).thenReturn(mockBill);

        boolean isSnoozed = billService.snoozeBillService(mockBill, newDate, userId);

        assertTrue(isSnoozed);
        assertEquals(newDate, mockBill.getDueDate());
    }

    @Test
    void testMarkBillAsPaidService() {
        Bill mockBill = new Bill();
        mockBill.setBillId(1);
        Mockito.when(billRepository.findByBillIdAndUserId(1, userId)).thenReturn(Optional.of(mockBill));
        Mockito.when(billRepository.save(any(Bill.class))).thenReturn(mockBill);

        boolean isMarkedPaid = billService.markBillAsPaidService(mockBill, userId);

        assertTrue(isMarkedPaid);
        assertEquals("paid", mockBill.getPaymentStatus());
    }

    @Test
    void testGetBillsOverview() {
        List<Bill> mockBills = List.of(new Bill(), new Bill());
        Date fromDate = new Date();
        Date toDate = new Date();
        String category = "Utility";
        String status = "Unpaid";

        Mockito.when(billRepository.findByBillCategoryAndDueDateBetweenAndPaymentStatusAndUserId(
                category, fromDate, toDate, status, userId))
               .thenReturn(mockBills);

        List<Bill> billsOverview = billService.getBillsOverview(category, fromDate, toDate, status, userId);

        assertNotNull(billsOverview);
        assertEquals(2, billsOverview.size());
    }
}
