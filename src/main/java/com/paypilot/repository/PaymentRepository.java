package com.paypilot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paypilot.model.Bill;
import com.paypilot.model.Payment;
import com.paypilot.model.PaymentOverview;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	@Query("SELECT p FROM Payment p JOIN p.bill b WHERE b.userId = :userId AND p.paymentDate BETWEEN :fromDate AND :toDate")
    List<Payment> findAllPaymentsByDateRange(@Param("userId") String userId,
                                             @Param("fromDate") Date fromDate,
                                             @Param("toDate") Date toDate);

    @Query("SELECT p FROM Payment p JOIN p.bill b WHERE b.userId = :userId AND p.category = :category AND p.paymentDate BETWEEN :fromDate AND :toDate")
    List<Payment> findPaymentsByCategoryAndDateRange(@Param("userId") String userId,
                                                     @Param("category") String category,
                                                     @Param("fromDate") Date fromDate,
                                                     @Param("toDate") Date toDate);

    @Query("SELECT p FROM Payment p JOIN p.bill b WHERE b.userId = :userId AND p.category = :category AND p.status = :status")
    List<Payment> findByCategoryAndStatus(@Param("userId") String userId,
                                          @Param("category") String category,
                                          @Param("status") String status);

    @Query("SELECT p FROM Payment p JOIN p.bill b WHERE b.userId = :userId AND p.status = :status")
    List<Payment> findByStatus(@Param("userId") String userId,
                               @Param("status") String status);

    @Query("SELECT new com.paypilot.model.PaymentOverview(" +
           "p.category, " +
           "COUNT(p), " +
           "SUM(p.amountPaid), " +
           "AVG(p.amountPaid), " +
           "SUM(CASE WHEN p.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN p.status = 'FAILED' THEN 1 ELSE 0 END)) " +
           "FROM Payment p JOIN p.bill b " +
           "WHERE b.userId = :userId AND p.category = :category AND p.paymentDate BETWEEN :fromDate AND :toDate " +
           "GROUP BY p.category")
    List<PaymentOverview> getPaymentOverview(@Param("userId") String userId,
                                              @Param("category") String category,
                                              @Param("fromDate") Date fromDate,
                                              @Param("toDate") Date toDate);

    @Query("SELECT new com.paypilot.model.PaymentOverview(" +
           "p.category, " +
           "COUNT(p), " +
           "SUM(p.amountPaid), " +
           "AVG(p.amountPaid), " +
           "SUM(CASE WHEN p.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN p.status = 'FAILED' THEN 1 ELSE 0 END)) " +
           "FROM Payment p JOIN p.bill b " +
           "WHERE b.userId = :userId AND p.paymentDate BETWEEN :fromDate AND :toDate " +
           "GROUP BY p.category")
    List<PaymentOverview> getPaymentOverview(@Param("userId") String userId,
                                              @Param("fromDate") Date fromDate,
                                              @Param("toDate") Date toDate);
    
    // Find payments by billId
    List<Payment> findByBillBillId(int billId);
   
    List<Payment> findByBillAndStatus(Bill bill, String status);
}
