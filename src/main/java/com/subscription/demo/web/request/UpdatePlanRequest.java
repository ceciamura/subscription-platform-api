package com.subscription.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class UpdatePlanRequest {
    @NotBlank
    private String name;
    @Positive
    private double monthlyPrice;

    public UpdatePlanRequest() {
    }

    public UpdatePlanRequest(String name, double monthlyPrice) {
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }


    @Override
    public String toString() {
        return "CreatePlanRequest{" +
                "name='" + name + '\'' +
                ", monthlyPrice=" + monthlyPrice +
                '}';
    }
}
