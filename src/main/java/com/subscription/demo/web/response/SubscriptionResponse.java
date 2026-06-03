package com.subscription.demo.web.response;

import com.subscription.demo.domain.Plan;

public class SubscriptionResponse {

    private String id;
    private String customerEmail;
    private Plan plan;

    public SubscriptionResponse(String id, String customerEmail, Plan plan) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.plan = plan;
    }

    public String getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Plan getPlan() {
        return plan;
    }
}