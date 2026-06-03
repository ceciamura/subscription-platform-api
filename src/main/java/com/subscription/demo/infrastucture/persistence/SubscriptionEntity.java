package com.subscription.demo.infrastucture.persistence;


import jakarta.persistence.*;

@Table(name = "subscriptions")
@Entity
public class SubscriptionEntity {

    @Id
    private String id;
    private String customerEmail;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanEntity plan;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(String id, String customerEmail, PlanEntity plan) {
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


    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }


    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }
}
