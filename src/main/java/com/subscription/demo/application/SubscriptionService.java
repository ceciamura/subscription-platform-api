package com.subscription.demo.application;

import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.SubscriptionRepository;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;
import com.subscription.demo.web.exception.SubscriptionNotFoundException;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    public Subscription newSubscription(CreateSubscriptionRequest createSubscriptonRequest) {

        String newId = UUID.randomUUID().toString();

        Subscription subscription =  new Subscription(newId,
                                createSubscriptonRequest.getCustomerEmail(),
                                createSubscriptonRequest.getMonthlyPrice());

        SubscriptionEntity entity = new SubscriptionEntity(
                subscription.getId(),
                subscription.getCustomerEmail(),
                subscription.getMonthlyPrice()
        );

        SubscriptionEntity savedEntity = subscriptionRepository.save(entity);
        return new Subscription(savedEntity.getId(), savedEntity.getCustomerEmail(), savedEntity.getMonthlyPrice());
    }

    public List<Subscription> getSubscriptions() {
        List<SubscriptionEntity> subscriptionEntityList = subscriptionRepository.findAll();

      return  subscriptionEntityList.stream()
              .map(entity -> new Subscription(
                      entity.getId(),
                      entity.getCustomerEmail(),
                      entity.getMonthlyPrice()))
              .toList();
    }

    public Subscription getSubscriptionById(String id){
        SubscriptionEntity entity = subscriptionRepository
                .findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));


        return new Subscription(entity.getId(), entity.getCustomerEmail(), entity.getMonthlyPrice());
    }

    public void deleteSubscriptionById(String id) {

            SubscriptionEntity entity = subscriptionRepository.findById(id)
                    .orElseThrow(() -> new SubscriptionNotFoundException(id));

            subscriptionRepository.delete(entity);
    }

    public Subscription updateSubscription(UpdateSubscriptionRequest request, String id) {

        SubscriptionEntity entity = subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));

        entity.setCustomerEmail(request.getCustomerEmail());
        entity.setMonthlyPrice(request.getMonthlyPrice());

        SubscriptionEntity updatedSubscription = subscriptionRepository.save(entity);

        return new Subscription(updatedSubscription.getId(), updatedSubscription.getCustomerEmail(), updatedSubscription.getMonthlyPrice());

    }
}
