package com.paypilot.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paypilot.service.PaymentManagerService;

@Component
public class ScheduledTasks {

    @Autowired
    private PaymentManagerService paymentManagerService;

    // Schedule the task to run at midnight every day
    // @Scheduled(cron = "0 0 0 * * *") // Midnight every day
    // Cron job that runs every 5 minutes
    @Scheduled(cron = "0 */1 * * * *")  // Every 5 minutes
    public void processScheduledPaymentsAtMidnight() {
    	System.out.println("Scheduled task running at: " + new java.util.Date());
        paymentManagerService.processScheduledPayments();
//    	System.out.println("Hello Cron Job Here");
    }
    
}

