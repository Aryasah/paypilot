package com.paypilot.model;

public class PaymentOverview {
    private String category;
    private long totalPayments;
    private double totalAmount;
    private double averageAmount;
    private long successfulPayments;
    private long failedPayments;

    // Constructors
    public PaymentOverview() {}

    public PaymentOverview(String category, long totalPayments, double totalAmount, double averageAmount,
                           long successfulPayments, long failedPayments) {
        this.category = category;
        this.totalPayments = totalPayments;
        this.totalAmount = totalAmount;
        this.averageAmount = averageAmount;
        this.successfulPayments = successfulPayments;
        this.failedPayments = failedPayments;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    public long getSuccessfulPayments() {
        return successfulPayments;
    }

    public void setSuccessfulPayments(long successfulPayments) {
        this.successfulPayments = successfulPayments;
    }

    public long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(long failedPayments) {
        this.failedPayments = failedPayments;
    }

    @Override
    public String toString() {
        return "PaymentOverview [category=" + category + ", totalPayments=" + totalPayments + ", totalAmount="
                + totalAmount + ", averageAmount=" + averageAmount + ", successfulPayments=" + successfulPayments
                + ", failedPayments=" + failedPayments + "]";
    }
}
