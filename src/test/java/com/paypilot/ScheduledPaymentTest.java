package com.paypilot;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.paypilot.model.Payee;
import com.paypilot.model.ScheduledPayment;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduledPaymentTest {

//	private RestTemplate restTemplate;
//
//    private String baseUrl;
//
//    @BeforeEach
//    public void setup() {
//        baseUrl = "http://localhost:8082/scheduled_payment";
//        restTemplate = new RestTemplate();
//    }
//
//    /**
//     * Test scheduling a valid payment.
//     * This test ensures that a valid payment request results in a scheduled payment with a 201 Created status.
//     */
//    @Test
//    public void testSchedulePayment_Success() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        scheduledPayment.setPayee(new Payee(1, "John Doe", "123 Main St, Springfield", "Account: 123456789, Bank: XYZ Bank"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        ResponseEntity<ScheduledPayment> response = restTemplate.exchange(
//            baseUrl + "/schedule", HttpMethod.POST, requestEntity, ScheduledPayment.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody() instanceof ScheduledPayment).isTrue();
//    }
//
//    /**
//     * Test scheduling payment when the payee does not exist.
//     * This test verifies that the system responds with a 400 Bad Request status when attempting to schedule a payment
//     * with a non-existent payee.
//     */
//    @Test
//    public void testSchedulePayment_PayeeNotFound() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        /*
//         * Ensure that the payee with such id is not present in database
//        */
//        scheduledPayment.setPayee(new Payee(999, "None", "None Existence", "None"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(
//                baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class
//            );
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 400 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Payee with ID 999 does not exist.");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the payee name does not match the one in the database.
//     * This test verifies that the system responds with a 409 Conflict status when attempting to schedule
//     * a payment where the payee name provided in the request does not match the payee name in the database.
//     */
//    @Test
//    public void testSchedulePayment_PayeeNameMismatch() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Assume that Payee with ID 4 exists, but the name is incorrect
//        scheduledPayment.setPayee(new Payee(4, "John Doe", "123 Main St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(
//                baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class
//            );
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 409 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Payee details do not match the existing payee with ID 4");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the payee address does not match the one in the database.
//     * This test verifies that the system responds with a 409 Conflict status when attempting to schedule
//     * a payment where the payee address provided in the request does not match the payee address in the database.
//     */
//    @Test
//    public void testSchedulePayment_PayeeAddressMismatch() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Assume that Payee with ID 4 exists, but the address is incorrect
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "456 Elm St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(
//                baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class
//            );
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 409 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Payee details do not match the existing payee with ID 4");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the payee bank details do not match the one in the database.
//     * This test verifies that the system responds with a 409 Conflict status when attempting to schedule
//     * a payment where the payee bank details provided in the request do not match the payee bank details in the database.
//     */
//    @Test
//    public void testSchedulePayment_PayeeBankDetailsMismatch() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Assume that Payee with ID 4 exists, but the bank details are incorrect
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "123 Main St, Springfield", "Bank ABC, Account 987654321"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(
//                baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class
//            );
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 409 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Payee details do not match the existing payee with ID 4");
//        }
//    }
//    
//    /**
//     * Test scheduling payment when the scheduled date is not in the future.
//     * This test verifies that the system responds with a 400 Bad Request status when attempting to schedule
//     * a payment with a date that is not in the future.
//     */
//    @Test
//    public void testSchedulePayment_InvalidScheduledDate() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        
//        // Set date to the past
//        Date pastDate = new Date(System.currentTimeMillis() - 10000000);
//        scheduledPayment.setScheduledDate(pastDate);
//        
//        // Set other valid fields
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "123 Main St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            restTemplate.exchange(baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class);
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 400 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            // Assert that the response body contains the expected error message
//            System.out.println(e.getResponseBodyAsString());
//            assertThat(e.getResponseBodyAsString()).contains("Scheduled date must be in the future");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the amount is not positive.
//     * This test verifies that the system responds with a 400 Bad Request status when attempting to schedule
//     * a payment with a non-positive amount.
//     */
//    @Test
//    public void testSchedulePayment_InvalidAmount() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Set an invalid amount (negative)
//        scheduledPayment.setAmount(-500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "123 Main St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            restTemplate.exchange(baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class);
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 400 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Amount must be a positive value");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the payer account number is empty or invalid.
//     * This test verifies that the system responds with a 400 Bad Request status when attempting to schedule
//     * a payment with an empty or invalid payer account number.
//     */
//    @Test
//    public void testSchedulePayment_InvalidPayerAccountNumber() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Set an invalid payer account number (empty)
//        scheduledPayment.setPayerAccountNumber("");
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "123 Main St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            restTemplate.exchange(baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class);
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 400 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Payer account number cannot be blank");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the payment frequency is invalid.
//     * This test verifies that the system responds with a 400 Bad Request status when attempting to schedule
//     * a payment with an invalid payment frequency.
//     */
//    @Test
//    public void testSchedulePayment_InvalidPaymentFrequency() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Set an invalid payment frequency
//        scheduledPayment.setPaymentFrequency("YEARLY");
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "123 Main St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            restTemplate.exchange(baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class);
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 400 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Payment frequency must be one of DAILY, WEEKLY, MONTHLY, QUARTERLY, HALF_YEARLY, or ANNUAL");
//        }
//    }
//
//    /**
//     * Test scheduling payment when the payment mode is invalid.
//     * This test verifies that the system responds with a 400 Bad Request status when attempting to schedule
//     * a payment with an invalid payment mode.
//     */
//    @Test
//    public void testSchedulePayment_InvalidPaymentMode() {
//        ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        
//        // Set an invalid payment mode
//        scheduledPayment.setPaymentMode("cheque");  // Assuming only bank_transfer, cash, and card are allowed
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setPayee(new Payee(4, "Jane Doe", "123 Main St, Springfield", "Bank XYZ, Account 123456789"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        try {
//            restTemplate.exchange(baseUrl + "/schedule", HttpMethod.POST, requestEntity, String.class);
//        } catch (HttpClientErrorException e) {
//            // Assert that we received a 400 status
//            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            // Assert that the response body contains the expected error message
//            assertThat(e.getResponseBodyAsString()).contains("Invalid payment mode as of now we are supporting CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER|PAYPAL as mode of payment");
//        }
//    }
//
//    /**
//     * Test canceling a scheduled payment.
//     * This test ensures that the cancel endpoint updates the payment status to "CANCELLED".
//     */
//    @Test
//    public void testCancelScheduledPayment() {
//    	ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        scheduledPayment.setPayee(new Payee(1, "John Doe", "123 Main St, Springfield", "Account: 123456789, Bank: XYZ Bank"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        ResponseEntity<ScheduledPayment> response = restTemplate.exchange(
//            baseUrl + "/schedule", HttpMethod.POST, requestEntity, ScheduledPayment.class
//        );        
//        HttpEntity<ScheduledPayment> cancelRequest = new HttpEntity<>(response.getBody(), headers);
//        ResponseEntity<ScheduledPayment> responseCancel = restTemplate.exchange(
//            baseUrl + "/cancel", HttpMethod.POST, cancelRequest, ScheduledPayment.class
//        );
//
//        assertThat(responseCancel.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseCancel.getBody()).isNotNull();
//        assertThat(responseCancel.getBody().getStatus()).isEqualTo("CANCELLED");
//    }
//
//    /**
//     * Test listing all scheduled payments.
//     * This test checks if the list of scheduled payments is returned correctly.
//     */
//    @Test
//    public void testGetScheduledPayments() {
//        // Assume some payments exist or create them if needed
//
//        ResponseEntity<ScheduledPayment[]> response = restTemplate.getForEntity(
//            baseUrl + "/list", ScheduledPayment[].class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody()).isNotEmpty();
//    }
//
//    /**
//     * Test modifying a scheduled payment.
//     * This test ensures that the modification endpoint updates the payment date correctly.
//     */
//    @Test
//    public void testModifyScheduledPayment() {
//    	ScheduledPayment scheduledPayment = new ScheduledPayment();
//        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
//        scheduledPayment.setPayee(new Payee(1, "John Doe", "123 Main St, Springfield", "Account: 123456789, Bank: XYZ Bank"));
//        scheduledPayment.setAmount(500.0);
//        scheduledPayment.setScheduledDate(futureDate);
//        scheduledPayment.setScheduled(true);
//        scheduledPayment.setPaymentMode("bank_transfer");
//        scheduledPayment.setPaymentFrequency("MONTHLY");
//        scheduledPayment.setPayerAccountNumber("9876543210");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ScheduledPayment> requestEntity = new HttpEntity<>(scheduledPayment, headers);
//
//        ResponseEntity<ScheduledPayment> response = restTemplate.exchange(
//            baseUrl + "/schedule", HttpMethod.POST, requestEntity, ScheduledPayment.class
//        );
//
//        // Now modify it
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date newDate = null;
//        try {
//            newDate = dateFormat.parse("2024-12-12");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        HttpEntity<ScheduledPayment> modifyRequest = new HttpEntity<>(response.getBody(), headers);
//        ResponseEntity<ScheduledPayment> responseModified = restTemplate.exchange(
//            baseUrl + "/modify?newDate=2024-12-12", HttpMethod.PUT, modifyRequest, ScheduledPayment.class
//        );
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(newDate);
//        Calendar calendar2 = Calendar.getInstance();
//        Date modifiedDate = responseModified.getBody().getScheduledDate();
//        calendar2.setTime(modifiedDate);
//        
//        calendar1.set(Calendar.HOUR_OF_DAY,0);
//        calendar1.set(Calendar.MINUTE, 0);
//        calendar1.set(Calendar.SECOND, 0);
//        calendar1.set(Calendar.MILLISECOND, 0);
//        
//        calendar2.set(Calendar.HOUR_OF_DAY, 0);
//        calendar2.set(Calendar.MINUTE, 0);
//        calendar2.set(Calendar.SECOND, 0);
//        calendar2.set(Calendar.MILLISECOND, 0);
//        assertThat(responseModified.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseModified.getBody()).isNotNull();
//        assertThat(calendar1.getTime()).isEqualTo(calendar2.getTime());
//        
//    }
}
