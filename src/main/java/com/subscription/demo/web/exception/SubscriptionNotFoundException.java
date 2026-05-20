package com.subscription.demo.web.exception;

public class SubscriptionNotFoundException extends RuntimeException{
    public SubscriptionNotFoundException(String id) {
        super("Subscription with id: " + id + " not found.");
    }
}
