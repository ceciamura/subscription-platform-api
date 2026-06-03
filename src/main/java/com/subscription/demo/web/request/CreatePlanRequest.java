package com.subscription.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CreatePlanRequest {
    @NotBlank
    private String name;
    @Positive
    private double monthlyPrice;

    public CreatePlanRequest() {
    }

    public CreatePlanRequest(String name, double monthlyPrice) {
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @Override
    public String toString() {
        return "CreatePlanRequest{" +
                "name='" + name + '\'' +
                ", monthlyPrice=" + monthlyPrice +
                '}';
    }
}
