package com.paypilot.repository;

import com.paypilot.model.Bill;
import com.paypilot.model.ScheduledPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Integer> {
	// Derived query to find scheduled payments by billId
    List<ScheduledPayment> findByBillBillId(Integer billId);
    
    Optional<ScheduledPayment> findByScheduledPaymentId(Integer scheduledPaymentId);
    
    // Custom query to find payments that are scheduled for today
    @Query("SELECT sp FROM ScheduledPayment sp WHERE sp.scheduledDate = :today AND sp.status = 'SCHEDULED'")
    List<ScheduledPayment> findScheduledPaymentsDueToday(@Param("today") Date today);

    // join query to fetch scheduled payments by userId via bills
    @Query("SELECT sp FROM ScheduledPayment sp JOIN Bill b ON sp.bill.billId = b.billId WHERE b.userId = :userId")
    List<ScheduledPayment> findScheduledPaymentsByUserId(@Param("userId") String userId);
    
    List<ScheduledPayment> findByBillAndStatus(Bill bill, String status);
}
