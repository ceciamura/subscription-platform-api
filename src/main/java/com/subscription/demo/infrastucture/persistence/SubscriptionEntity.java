package com.subscription.demo.infrastucture.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "subscriptions")
@Entity
public class SubscriptionEntity {

    @Id
    private String id;
    private String customerEmail;
    private Double monthlyPrice;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(String id, String customerEmail, Double monthlyPrice) {
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
