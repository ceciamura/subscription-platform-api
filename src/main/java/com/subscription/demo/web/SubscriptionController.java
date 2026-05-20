package com.subscription.demo.web;

import com.subscription.demo.application.SubscriptionService;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    public SubscriptionController(SubscriptionService subscriptonService) {
        this.subscriptionService = subscriptonService;
    }


    @PostMapping("/subscriptions")
    public Subscription newSubscription(@Valid @RequestBody CreateSubscriptionRequest subscription){

        return subscriptionService.newSubscription(subscription);

    }

    @GetMapping("/subscriptions")
    public List<Subscription> getSubscriptions(){
        return subscriptionService.getSubscriptions();
    }
}
