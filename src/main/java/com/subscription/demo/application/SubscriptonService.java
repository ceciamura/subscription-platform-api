package com.subscription.demo.application;

import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscriptonService {
    public Subscription newSubscription(CreateSubscriptionRequest createSubscriptonRequest) {

        String newId = UUID.randomUUID().toString();

        return new Subscription(newId,
                                createSubscriptonRequest.getCustomerEmail(),
                                createSubscriptonRequest.getMonthlyPrice());
    }
}
