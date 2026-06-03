package com.subscription.demo.infrastucture.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plans")
public class PlanEntity {
    @Id
    private String id;
    private String name;
    private double monthlyPlan;

    public PlanEntity(String id, String name, double monthlyPlan) {
        this.id = id;
        this.name = name;
        this.monthlyPlan = monthlyPlan;
    }

    public PlanEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMonthlyPlan() {
        return monthlyPlan;
    }

    public void setMonthlyPlan(double monthlyPlan) {
        this.monthlyPlan = monthlyPlan;
    }

    @Override
    public String toString() {
        return "PlanEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", monthlyPlan=" + monthlyPlan +
                '}';
    }
}
