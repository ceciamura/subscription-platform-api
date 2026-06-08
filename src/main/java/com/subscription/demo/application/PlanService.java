package com.subscription.demo.application;

import com.subscription.demo.domain.Plan;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.PlanEntity;
import com.subscription.demo.infrastucture.persistence.PlanRepository;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;
import com.subscription.demo.infrastucture.persistence.SubscriptionRepository;
import com.subscription.demo.web.exception.PlanNotFoundException;
import com.subscription.demo.web.request.CreatePlanRequest;
import com.subscription.demo.web.request.UpdatePlanRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public class PlanService {

    private final  PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public PlanService(PlanRepository planRepository, SubscriptionRepository subscriptionRepository) {
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
    }


    public Plan createNewPlan(@Valid CreatePlanRequest createPlanRequest){

        String  id = UUID.randomUUID().toString();

        Plan plan = new Plan(
                id,
                createPlanRequest.getName(),
                createPlanRequest.getMonthlyPrice());

        PlanEntity planEntity = new PlanEntity(
                plan.getId(),
                plan.getName(),
                plan.getMonthlyPrice());

        PlanEntity savedEntity = planRepository.save(planEntity);

        return  new Plan(savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getMonthlyPlan());
    }

    public List<Plan> getPlans(){

        List<PlanEntity> entityList = planRepository.findAll();

        return entityList.stream()
                .map(entity -> new Plan(entity.getId(), entity.getName(), entity.getMonthlyPlan()))
                .toList();
    }

    public Plan getPlanById(String id){
        PlanEntity entity = planRepository.findById(id)
                .orElseThrow(()-> new PlanNotFoundException(id));
        return new Plan(entity.getId(), entity.getName(), entity.getMonthlyPlan());
    }

    public void deletePlanById(String id){
        PlanEntity entity = planRepository.findById(id)
                .orElseThrow(()-> new PlanNotFoundException(id));
        planRepository.deleteById(entity.getId());

    }

    public Plan updatePlan(UpdatePlanRequest request, String id){
        PlanEntity planFound = planRepository.findById(id).orElseThrow(()-> new PlanNotFoundException(id));

        planFound.setName(request.getName());
        planFound.setMonthlyPlan(request.getMonthlyPrice());

        PlanEntity planUpdated = planRepository.save(planFound);

        return new Plan(planUpdated.getId(), planUpdated.getName(), planUpdated.getMonthlyPlan());
    }

    public List<Subscription>getSubscriptionByIdPlan(String planId){
        planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException(planId));

        List<SubscriptionEntity> entities =
                subscriptionRepository.findByPlanId(planId);

        return entities.stream()
                .map(entity -> new Subscription(
                        entity.getId(),
                        entity.getCustomerEmail(),
                        new Plan(
                                entity.getPlan().getId(),
                                entity.getPlan().getName(),
                                entity.getPlan().getMonthlyPlan()
                        )
                ))
                .toList();
    }

}
