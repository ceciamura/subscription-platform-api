package com.subscription.demo.web;

import com.subscription.demo.application.SubscriptonService;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    private final SubscriptonService subscriptonService;


    public SubscriptionController(SubscriptonService subscriptonService) {
        this.subscriptonService = subscriptonService;
    }


    @PostMapping("/subscriptions")
    public Subscription newSubscription(@Valid @RequestBody CreateSubscriptionRequest subscription){

        return subscriptonService.newSubscription(subscription);

    }
}
