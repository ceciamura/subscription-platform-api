package com.subscription.demo.infrastucture.persistence.mapper;


import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;

public class SubscriptionEntityMapper {

    public static Subscription toDomain(SubscriptionEntity subscriptionEntity){

        return new Subscription(
                subscriptionEntity.getId(),
                subscriptionEntity.getCustomerEmail(),
                PlanEntityMapper.toDomain(subscriptionEntity.getPlan())
        );
    }

    public static SubscriptionEntity toEntity(Subscription subscription){

        return new SubscriptionEntity(
                subscription.getId(),
                subscription.getCustomerEmail(),
                PlanEntityMapper.toEntity(subscription.getPlan())
        );
    }
}
