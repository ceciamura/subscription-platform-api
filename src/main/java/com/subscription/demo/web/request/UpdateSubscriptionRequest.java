package com.subscription.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class UpdateSubscriptionRequest {
    @NotBlank
    private String customerEmail;
    @Positive
    private Double monthlyPrice;

    public UpdateSubscriptionRequest(String customerEmail, Double monthlyPrice) {
        this.customerEmail = customerEmail;
        this.monthlyPrice = monthlyPrice;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }
}
