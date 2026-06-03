package com.subscription.demo.web.mapper;

import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.response.SubscriptionResponse;

public class SubscriptionResponseMapper {

    public static SubscriptionResponse toResponse(Subscription subscription){
            return new SubscriptionResponse(
                    subscription.getId(),
                    subscription.getCustomerEmail(),
                    subscription.getMonthlyPrice());

    }
}
