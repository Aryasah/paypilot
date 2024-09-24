package com.paypilot.controller;

import com.paypilot.model.Payee;
import com.paypilot.service.PayeeService;
import com.paypilot.utils.ValidationUtil;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payees")
@CrossOrigin(origins = "http://localhost:3000")
public class PayeeController {

    private PayeeService payeeService;
    private final Validator validator;
    @Autowired
    public PayeeController(PayeeService payeeService, Validator validator) {
        this.payeeService = payeeService;
        this.validator = validator;
    }

    @GetMapping
    public List<Payee> getAllPayees() {
        return payeeService.getAllPayees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payee> getPayeeById(@PathVariable int id) {
        Optional<Payee> payee = payeeService.getPayeeById(id);
        return payee.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Payee> getPayeeByName(@RequestParam String payeeName) {
        Payee payee = payeeService.getPayeeByName(payeeName);
        if (payee != null) {
            return ResponseEntity.ok(payee);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createPayee(@Valid @RequestBody Payee payee) {
    	 ResponseEntity<?> validationErrorResponse = ValidationUtil.validateEntity(payee, validator);
         if (validationErrorResponse != null) {
             return validationErrorResponse; // If there are errors, return the response
         }
        Payee newPayee = payeeService.createPayee(payee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPayee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payee> updatePayee(@PathVariable int id, @RequestBody Payee updatedPayee) {
        Optional<Payee> existingPayee = payeeService.getPayeeById(id);
        if (existingPayee.isPresent()) {
            updatedPayee.setPayeeId(id);
            return ResponseEntity.ok(payeeService.updatePayee(updatedPayee));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayee(@PathVariable int id) {
        payeeService.deletePayee(id);
        return ResponseEntity.noContent().build();
    }
}
