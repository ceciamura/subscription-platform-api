package com.subscription.demo.web.exception;

public class PlanNotFoundException extends RuntimeException{
    public PlanNotFoundException(String id) {
        super("Plan with id: " + id + " not found.");
    }
}
