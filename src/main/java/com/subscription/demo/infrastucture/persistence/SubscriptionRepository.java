package com.subscription.demo.infrastucture.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository  extends JpaRepository<SubscriptionEntity, String> {

    List<SubscriptionEntity> findByPlanId(String id);
    List<SubscriptionEntity> findByCustomerEmailContainingIgnoreCase(String email);
}
