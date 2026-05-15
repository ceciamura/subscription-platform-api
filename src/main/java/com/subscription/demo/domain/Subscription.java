package com.subscription.demo.domain;

public class Subscription {

    private String id;
    private String customerEmail;
    private Double monthlyPrice;

    public Subscription() {
    }

    public Subscription(String id, String customerEmail, Double monthlyPrice) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.monthlyPrice = monthlyPrice;
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

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", monthlyPrice=" + monthlyPrice +
                '}';
    }
}
