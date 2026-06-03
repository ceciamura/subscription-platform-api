package com.subscription.demo.web.response;

public class PlanResponse {

    private String id;
    private String name;
    private Double monthlyPrice;

    public PlanResponse(String id, String name, Double monthlyPrice) {
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

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }
}
