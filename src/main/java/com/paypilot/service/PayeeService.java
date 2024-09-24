package com.paypilot.service;

import com.paypilot.model.Payee;
import com.paypilot.repository.PayeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayeeService {

    @Autowired
    private PayeeRepository payeeRepository;

    public List<Payee> getAllPayees() {
        return payeeRepository.findAll();
    }

    public Optional<Payee> getPayeeById(int id) {
        return payeeRepository.findById(id);
    }

    public Payee getPayeeByName(String payeeName) {
        return payeeRepository.findByPayeeName(payeeName);
    }

    public Payee createPayee(Payee payee) {
        return payeeRepository.save(payee);
    }

    public void deletePayee(int id) {
        payeeRepository.deleteById(id);
    }

    public Payee updatePayee(Payee payee) {
        return payeeRepository.save(payee);
    }
}
