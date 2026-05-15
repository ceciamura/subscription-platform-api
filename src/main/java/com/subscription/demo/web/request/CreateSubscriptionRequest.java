package com.subscription.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CreateSubscriptionRequest {

    @NotBlank
    private String customerEmail;
    @Positive
    private Double monthlyPrice;

    public CreateSubscriptionRequest() {
    }

    public CreateSubscriptionRequest(String customerEmail, Double monthlyPrice) {
        this.customerEmail = customerEmail;
        this.monthlyPrice = monthlyPrice;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @Override
    public String toString() {
        return "CreateSubscriptionRequest{" +
                "customerEmail='" + customerEmail + '\'' +
                ", monthlyPrice=" + monthlyPrice +
                '}';
    }
}
