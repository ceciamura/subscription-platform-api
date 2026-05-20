package com.subscription.demo.infrastucture.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository  extends JpaRepository<SubscriptionEntity, String> {}
