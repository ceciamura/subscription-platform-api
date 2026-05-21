package com.subscription.demo.web.response;

public class SubscriptionResponse {

    private String id;
    private String customerEmail;
    private Double monthlyPrice;

    public SubscriptionResponse(String id, String customerEmail, Double monthlyPrice) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.monthlyPrice = monthlyPrice;
    }

    public String getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }
}
