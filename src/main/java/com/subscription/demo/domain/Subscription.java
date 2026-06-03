package com.subscription.demo.domain;

import jakarta.persistence.*;


public class Subscription {

    private String id;
    private String customerEmail;
    private Plan plan;

    public Subscription() {
    }

    public Subscription(String id, String customerEmail, Plan plan) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.plan = plan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", plan=" + plan +
                '}';
    }
}
