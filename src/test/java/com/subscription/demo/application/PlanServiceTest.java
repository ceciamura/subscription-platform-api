package com.subscription.demo.application;

import com.subscription.demo.domain.Plan;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.PlanEntity;
import com.subscription.demo.infrastucture.persistence.PlanRepository;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;
import com.subscription.demo.infrastucture.persistence.SubscriptionRepository;
import com.subscription.demo.web.request.CreatePlanRequest;
import com.subscription.demo.web.request.UpdatePlanRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class PlanServiceTest {

    private final PlanRepository repository = Mockito.mock(PlanRepository.class);
    private final SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);

    private final PlanService planService = new PlanService(repository, subscriptionRepository);


    @Test
    public void shouldReturnAnewPlan(){

        CreatePlanRequest request = new CreatePlanRequest(
                "aa", 10.0);

       Mockito.when(repository.save(any())).thenAnswer(i-> i.getArgument(0));

       var result = planService.createNewPlan(request);

        assertNotNull(result);
        assertEquals("aa", result.getName());
        assertEquals(10.0, result.getMonthlyPrice());

    }

    @Test
    public void shouldGetAllPlans(){

        List<PlanEntity> entityList = List.of(
                new PlanEntity("1", "aa", 10.0));

        Mockito.when(repository.findAll()).thenReturn(entityList);

        List<Plan> planList = planService.getPlans();

        assertEquals(planList.size(), entityList.size());
        assertEquals("aa", planList.get(0).getName());
    }

    @Test
    public void shouldReturnAPlanById(){
        PlanEntity entity = new PlanEntity("1", "aa", 10.0);

        Mockito.when(repository.findById("1")).thenReturn(Optional.of(entity));

        Plan plan = planService.getPlanById("1");

        assertEquals(plan.getId(), entity.getId());
        assertEquals(plan.getName(), entity.getName());
        assertEquals(plan.getMonthlyPrice(), entity.getMonthlyPlan());

    }

    @Test
    public void shouldDeleteById(){
        PlanEntity entity = new PlanEntity("1", "aa", 10.0);


        Mockito.when(repository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        Mockito.doNothing().when(repository)
                .deleteById("1");

        planService.deletePlanById("1");

        Mockito.verify(repository, Mockito.times(1))
                .findById("1");

        // Verificamos que el servicio efectivamente llamó al método de borrar con el ID correcto
        Mockito.verify(repository, Mockito.times(1))
                .deleteById("1");
    }

    @Test
    public void shouldUpdateById(){
        UpdatePlanRequest request = new UpdatePlanRequest(
                "aa", 10.0);

        PlanEntity entity = new PlanEntity("1", "aa", 10.0);

        Mockito.when(repository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        Mockito.when(repository.save(any())).thenAnswer(i-> i.getArgument(0));

        planService.updatePlan(request, entity.getId());

        assertEquals("aa", entity.getName());
        assertEquals(10.0, entity.getMonthlyPlan());

        Mockito.verify(repository, Mockito.times(1)).findById("1");

        Mockito.verify(repository, Mockito.times(1)).save(entity);

    }

    @Test
    public void shouldReturnListOfSubscriptionByIdPlan(){

        PlanEntity entity = new PlanEntity("1", "aa", 10.0);
        List<SubscriptionEntity> subscriptionEntityList =
                List.of(new SubscriptionEntity("2", "cc", entity));

        Mockito.when(repository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        Mockito.when(subscriptionRepository.findByPlanId(entity.getId()))
                .thenReturn(subscriptionEntityList);

        List<Subscription> result =
                planService.getSubscriptionByIdPlan("1");

        assertEquals(1, result.size());
        assertEquals("cc", result.get(0).getCustomerEmail());
        assertEquals("1", result.get(0).getPlan().getId());

    }


}



