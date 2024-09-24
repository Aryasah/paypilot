package com.paypilot.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paypilot.model.Bill;

/**
 * Description: 
 * This interface is a Spring Data JPA repository for managing
 * `Bill` entities. 
 * It provides methods for querying bills based on specific criteria:
 * 
 * 
 * Author: Varsha Veeraraghavan 
 * Date: 30/8/2024 
 * Methods Written
 * - Finding bills with a due date after a specified date and a specific payment status
 * (`findByDueDateAfterAndPaymentStatus`) 
 * - Finding bills within a specific category, due date range, and payment status
 * (`findByBillCategoryAndDueDateBetweenAndPaymentStatus`)
 * 
 * Author Tanya Arora
 * Date: 31/8/2024 
 * Methods Written:
 * - Finding bills with a due date before a specified date and a specific payment status
 * (`findByDueDateBeforeAndPaymentStatus`)
 */

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	List<Bill> findByUserId(String userId);

    List<Bill> findByDueDateAfterAndPaymentStatusAndUserId(Date date, String status, String userId);

    List<Bill> findByBillCategoryAndDueDateBetweenAndPaymentStatusAndUserId(String category, Date fromDate, Date toDate, String status, String userId);

    List<Bill> findByDueDateBeforeAndPaymentStatusAndUserId(Date today, String status, String userId);

    Optional<Bill> findByBillIdAndUserId(int billId, String userId);
    
    
    @Query("SELECT b FROM Bill b WHERE b.userId = :userId AND b.billCategory = :billCategory AND b.dueDate BETWEEN :fromDate AND :toDate")
	List<Bill> findByUserIdAndCategoryAndDueDateBetween(
		@Param("userId") String userId,
		@Param("billCategory") String billCategory,
		@Param("fromDate") LocalDate fromDate,
		@Param("toDate") LocalDate toDate
	);

	@Query("SELECT b FROM Bill b WHERE b.userId = :userId AND b.billCategory = :billCategory")
	List<Bill> findByUserIdAndCategory(
	    @Param("userId") String userId,
	    @Param("billCategory") String billCategory
	);
	
	@Query("SELECT b FROM Bill b WHERE b.userId = :userId AND b.dueDate BETWEEN :fromDate AND :toDate")
	List<Bill> findByUserIdAndDueDateBetween(
	    @Param("userId") String userId,
	    @Param("fromDate") LocalDate fromDate,
		@Param("toDate") LocalDate toDate
	);
	
	/**
	 * Arya
	 * fetch bills by userId
	 */
	@Query("SELECT b FROM Bill b JOIN User u ON b.userId = u.userId WHERE u.userId = :userId")
	List<Bill> findBillsByUserId(@Param("userId") String userId);
	
	// Query when a specific category is provided
	List<Bill> findByBillCategoryAndDueDateBetweenAndUserId(
	    String billCategory, 
	    Date fromDate, 
	    Date toDate, 
	    String userId
	);
	
	// Query when 'ALL' category is selected (ignores billCategory)
	List<Bill> findByDueDateBetweenAndUserId(
	    Date fromDate, 
	    Date toDate, 
	    String userId
	);
	List<Bill> findByBillCategoryAndUserId(
	        String billCategory, 
	        String userId
	    );
	
	Optional<Bill> findByBillId(int billId);
}
