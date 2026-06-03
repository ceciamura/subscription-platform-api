package com.subscription.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CreateSubscriptionRequest {

    @NotBlank
    private String customerEmail;
    @NotBlank
    private String planId;

    public CreateSubscriptionRequest() {
    }

    public CreateSubscriptionRequest(String customerEmail, String planId) {
        this.customerEmail = customerEmail;
        this.planId = planId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String plan_id) {
        this.planId = plan_id;
    }

    @Override
    public String toString() {
        return "CreateSubscriptionRequest{" +
                "customerEmail='" + customerEmail + '\'' +
                ", plan_id='" + planId + '\'' +
                '}';
    }
}
