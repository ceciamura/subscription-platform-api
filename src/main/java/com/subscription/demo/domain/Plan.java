package com.subscription.demo.domain;

public class Plan {

    private String id;
    private String name;
    private double monthlyPrice;

    public Plan(String id, String name, double monthlyPrice) {
        this.id = id;
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", monthlyPrice=" + monthlyPrice +
                '}';
    }
}
