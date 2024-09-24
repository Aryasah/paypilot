/**
 * Author: Simran Singh
 * Date: 6th September 2024
 * 
 * Description: This is the test file for Sign-up , Login Validation and OTP verification.
 */

package com.paypilot;

import com.paypilot.dto.OtpVerificationRequest;
import com.paypilot.model.User;
import com.paypilot.repository.UserRepository;
import com.paypilot.controller.UserController;
import com.paypilot.service.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PaypilotApplicationTests {
//	
//	    @Mock
//	    private UserRepository userRepository;
//
//	    @Mock
//	    private OtpService otpService;
//
//	    @InjectMocks
//	    private UserController userController;
//
//	    private User mockUser;
//
//	    @BeforeEach
//	    public void setUp() {
//	        MockitoAnnotations.openMocks(this);
//	        mockUser = new User("test@example.com", "testuser", "password123", "PAN123", "123456789", "IFSC001", "BankName");
//	    }
//
//	    // Tests if a user can successfully sign up when the user does not exist.
//	    @Test
//	    public void testSignUp_Success() {
//	        when(userRepository.countByUserId(mockUser.getUserId())).thenReturn(0L);
//	        when(userRepository.save(mockUser)).thenReturn(mockUser);
//
//	        ResponseEntity<User> response = userController.signUp(mockUser);
//
//	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//	        assertNotNull(response.getBody());
//	        assertEquals(mockUser.getEmail(), response.getBody().getEmail());
//	    }
//
//	    // Tests when trying to sign up with an existing userId, expecting a conflict response.
//	    @Test
//	    public void testSignUp_UserAlreadyExists() {
//	        when(userRepository.countByUserId(mockUser.getUserId())).thenReturn(1L);
//
//	        ResponseEntity<User> response = userController.signUp(mockUser);
//
//	        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//	        assertNull(response.getBody());
//	    }
//	    
//        // Tests successful login using the email.
//	    @Test
//	    public void testLogin_Success_WithEmail() {
//	        Map<String, String> credentials = new HashMap<>();
//	        credentials.put("input", "test@example.com");
//	        credentials.put("password", "password123");
//
//	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
//
//	        ResponseEntity<Map<String, String>> response = userController.login(credentials);
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals("Login successful", response.getBody());
//	    }
//
//	    // Tests successful login using the userId.
//	    @Test
//	    public void testLogin_Success_WithUserId() {
//	        Map<String, String> credentials = new HashMap<>();
//	        credentials.put("input", "testuser");
//	        credentials.put("password", "password123");
//
//	        when(userRepository.findByUserId("testuser")).thenReturn(Optional.of(mockUser));
//
//	        ResponseEntity<Map<String, String>> response = userController.login(credentials);
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals("Login successful", response.getBody());
//	    }
//
//	    // Tests login with the incorrect password.
//	    @Test
//	    public void testLogin_IncorrectPassword() {
//	        Map<String, String> credentials = new HashMap<>();
//	        credentials.put("input", "test@example.com");
//	        credentials.put("password", "wrongpassword");
//
//	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
//
//	        ResponseEntity<Map<String, String>> response = userController.login(credentials);
//
//	        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//	        assertEquals("Incorrect password", response.getBody());
//	    }
//
//	    // Tests the login when the user does not exist.
//	    @Test
//	    public void testLogin_UserNotFound() {
//	        Map<String, String> credentials = new HashMap<>();
//	        credentials.put("input", "unknownuser");
//	        credentials.put("password", "password123");
//
//	        when(userRepository.findByUserId("unknownuser")).thenReturn(Optional.empty());
//
//	        ResponseEntity<Map<String, String>> response = userController.login(credentials);
//
//	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//	        assertEquals("User not found", response.getBody());
//	    }
//
//	    // Tests sending an OTP when a user with a valid email is found.
//	    @Test
//	    public void testSendOtp_Success() {
//	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
//
//	        ResponseEntity<String> response = userController.sendOtp("test@example.com");
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals("OTP sent to email", response.getBody());
//	        verify(otpService, times(1)).generateAndSendOtp("test@example.com");
//	    }
//
//	    // Tests sending OTP when the user is not found.
//	    @Test
//	    public void testSendOtp_UserNotFound() {
//	        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());
//
//	        ResponseEntity<String> response = userController.sendOtp("unknown@example.com");
//
//	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//	        assertEquals("User not found", response.getBody());
//	        verify(otpService, never()).generateAndSendOtp(anyString());
//	    }
//
//	    // Tests OTP verification when the OTP is correct.
//	    @Test
//	    public void testVerifyOtp_Success() {
//	        OtpVerificationRequest otpRequest = new OtpVerificationRequest();
//	        otpRequest.setEmail("test@example.com");
//	        otpRequest.setOtp("123456");
//
//	        when(otpService.verifyOtp("test@example.com", "123456")).thenReturn(true);
//
//	        ResponseEntity<Map<String, String>> response = userController.verifyOtp(otpRequest);
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertTrue(response.getBody().get("success").equals("true"));
//	        assertEquals("OTP Verified", response.getBody().get("message"));
//	    }
//
//	    // Tests OTP verification when the OTP is incorrect.
//	    @Test
//	    public void testVerifyOtp_Failure() {
//	        OtpVerificationRequest otpRequest = new OtpVerificationRequest();
//	        otpRequest.setEmail("test@example.com");
//	        otpRequest.setOtp("wrongotp");
//
//	        when(otpService.verifyOtp("test@example.com", "wrongotp")).thenReturn(false);
//
//	        ResponseEntity<Map<String, String>> response = userController.verifyOtp(otpRequest);
//
//	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//	        assertTrue(response.getBody().get("success").equals("false"));
//	        assertEquals("Invalid OTP or OTP expired", response.getBody().get("message"));
//	    }

}

