package com.paypilot.repository;

import com.paypilot.model.Payee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, Integer> {
    // Custom query methods if needed
    Payee findByPayeeName(String payeeName);
}
