package com.paypilot.utils;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {

    public static <T> ResponseEntity<?> validateEntity(T entity, Validator validator) {
        // Create a binding result to hold validation errors
        BindingResult bindingResult = new BeanPropertyBindingResult(entity, entity.getClass().getSimpleName());
        
        // Perform validation
        validator.validate(entity, bindingResult);
        
        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        
        // Return null if there are no errors
        return null;
    }
}
