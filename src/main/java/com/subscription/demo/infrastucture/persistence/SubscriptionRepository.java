package com.subscription.demo.infrastucture.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository  extends JpaRepository<SubscriptionEntity, String> {

    List<SubscriptionEntity> findByPlanId(String id);
}
