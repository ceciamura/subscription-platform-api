package com.subscription.demo.application;

import com.subscription.demo.domain.Plan;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.PlanEntity;
import com.subscription.demo.infrastucture.persistence.PlanRepository;
import com.subscription.demo.infrastucture.persistence.SubscriptionRepository;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;
import com.subscription.demo.web.exception.PlanNotFoundException;
import com.subscription.demo.web.exception.SubscriptionNotFoundException;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }


    public Subscription newSubscription(CreateSubscriptionRequest createSubscriptonRequest) {

        String newId = UUID.randomUUID().toString();

        PlanEntity planEntity = planRepository.findById(createSubscriptonRequest.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(createSubscriptonRequest.getPlanId()));

        Plan plan = new Plan(planEntity.getId(), planEntity.getName(), planEntity.getMonthlyPlan());


        Subscription subscription = new Subscription(newId,
                createSubscriptonRequest.getCustomerEmail(),
                plan);

        SubscriptionEntity entity = new SubscriptionEntity(
                subscription.getId(),
                subscription.getCustomerEmail(),
                planEntity
        );

        SubscriptionEntity savedEntity = subscriptionRepository.save(entity);
        return new Subscription(savedEntity.getId(), savedEntity.getCustomerEmail(), plan);
    }

    public List<Subscription> getSubscriptions() {
        List<SubscriptionEntity> subscriptionEntityList = subscriptionRepository.findAll();


        return subscriptionEntityList.stream()
                .map(entity -> new Subscription(
                        entity.getId(),
                        entity.getCustomerEmail(),
                        new Plan(entity.getPlan().getId(), entity.getPlan().getName(), entity.getPlan().getMonthlyPlan())))
                .toList();
    }

    public Subscription getSubscriptionById(String id) {
        SubscriptionEntity entity = subscriptionRepository
                .findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));


        return new Subscription(entity.getId(), entity.getCustomerEmail(), new Plan(entity.getPlan().getId(), entity.getPlan().getName(), entity.getPlan().getMonthlyPlan()));
    }

    public void deleteSubscriptionById(String id) {

        SubscriptionEntity entity = subscriptionRepository.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));

        subscriptionRepository.deleteById(entity.getId());
    }

    public Subscription updateSubscription(UpdateSubscriptionRequest request, String id) {

        SubscriptionEntity entity = subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));

        PlanEntity planEntity = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(request.getPlanId()));

        entity.setCustomerEmail(request.getCustomerEmail());
        entity.setPlan(planEntity);

        SubscriptionEntity updatedSubscription = subscriptionRepository.save(entity);

        return new Subscription(updatedSubscription.getId(), updatedSubscription.getCustomerEmail(), new Plan(entity.getPlan().getId(), entity.getPlan().getName(), entity.getPlan().getMonthlyPlan()));

    }

    public Page<Subscription> getSubscriptionPage(int page, int size, String sortBy, String direction){

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return subscriptionRepository.findAll(pageable)
                .map(entity -> new Subscription(
                        entity.getId(),
                        entity.getCustomerEmail(),
                        new Plan(
                                entity.getPlan().getId(),
                                entity.getPlan().getName(),
                                entity.getPlan().getMonthlyPlan()
                        )
                ));
    }

    public List<Subscription> getSubscriptionsByEmailSearch(String email){
        List<SubscriptionEntity> entities = subscriptionRepository.findByCustomerEmailContainingIgnoreCase(email);

        return entities.stream()
                .map(e -> new Subscription(e.getId(),
                                          e.getCustomerEmail(),
                                          new Plan(e.getPlan().getId(),
                                                  e.getPlan().getName(),
                                                  e.getPlan().getMonthlyPlan()))).toList();
    }

    public List<Subscription> getSubscriptionByPlanNameAndCustomerEmail(String planName, String email){
        List<SubscriptionEntity> entities = subscriptionRepository.findByPlanNameContainingIgnoreCaseAndCustomerEmailContainingIgnoreCase(planName, email);

        return entities.stream()
                .map(e -> new Subscription(e.getId(),
                        e.getCustomerEmail(),
                        new Plan(e.getPlan().getId(),
                                e.getPlan().getName(),
                                e.getPlan().getMonthlyPlan()))).toList();
    }
}
