package com.subscription.demo.application;

import com.subscription.demo.domain.Plan;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.PlanEntity;
import com.subscription.demo.infrastucture.persistence.PlanRepository;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;
import com.subscription.demo.infrastucture.persistence.SubscriptionRepository;
import com.subscription.demo.web.exception.PlanNotFoundException;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class SubscriptionServiceTest {


    private final SubscriptionRepository subscriptionRepository =
            Mockito.mock(SubscriptionRepository.class);

    private final PlanRepository planRepository =
            Mockito.mock(PlanRepository.class);

    private final SubscriptionService subscriptionService =
            new SubscriptionService(subscriptionRepository, planRepository);


    @Test
    void shouldCreateANewSubscription() {

        CreateSubscriptionRequest request =
                new CreateSubscriptionRequest(
                        "aaa@aa.com",
                        "2");

        PlanEntity planEntity = new PlanEntity("2", "Premium", 20.0);

        Mockito.when(planRepository.findById("2"))
                .thenReturn(Optional.of(planEntity));

        Mockito.when(subscriptionRepository.save(any())).
                thenAnswer(i -> i.getArgument(0));

        var result = subscriptionService.newSubscription(request);

        assertNotNull(result);
        assertEquals("aaa@aa.com", result.getCustomerEmail());
        assertEquals("2", result.getPlan().getId());

    }

    @Test
    void shouldReturnAllSubscriptions(){
        //given
        PlanEntity planEntity = new PlanEntity("2", "Premium", 20.0);

        Mockito.when(planRepository.findById("2"))
                .thenReturn(Optional.of(planEntity));;

       SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
               "1",
               "ww@ww.com",
               planEntity);
         List<SubscriptionEntity> entityList = List.of(subscriptionEntity);

         Mockito.when(subscriptionRepository.findAll()).thenReturn(entityList);

         List<Subscription> list = subscriptionService.getSubscriptions();

         assertEquals(list.size(), entityList.size());
         assertEquals("1", list.get(0).getId());
        assertEquals("ww@ww.com", list.get(0).getCustomerEmail());
        assertEquals("2", list.get(0).getPlan().getId());
    }

    @Test
    void shouldReturnSubscriptionById(){
        PlanEntity planEntity = new PlanEntity("2", "Premium", 20.0);


        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
                "1",
                "ww@ww.com",
                planEntity);

        Mockito.when(planRepository.findById("2"))
                .thenReturn(Optional.of(planEntity));
        Mockito.when(subscriptionRepository.findById(any()))
                .thenReturn((Optional.of(subscriptionEntity)));

        Subscription subscriptionFound = subscriptionService.
            getSubscriptionById(subscriptionEntity.getId());

        assertNotNull(subscriptionFound);
        assertEquals("1",subscriptionFound.getId() );
        assertEquals("ww@ww.com",subscriptionFound.getCustomerEmail() );
        assertEquals("2",subscriptionFound.getPlan().getId());
    }

    @Test
  void shouldDeleteASubscriptionById(){
        PlanEntity planEntity = new PlanEntity("2", "Premium", 20.0);
      SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
              "1",
              "ww@ww.com",
              planEntity);


        Mockito.when(planRepository.findById("2"))
                .thenReturn(Optional.of(planEntity));
      Mockito.when(subscriptionRepository.findById("1"))
              .thenReturn((Optional.of(subscriptionEntity)));

      Mockito.doNothing().when(subscriptionRepository)
              .deleteById("1");

      subscriptionService.deleteSubscriptionById(subscriptionEntity.getId());

      Mockito.verify(subscriptionRepository, Mockito.times(1)).findById("1");

        // Verificamos que el servicio efectivamente llamó al método de borrar con el ID correcto
        Mockito.verify(subscriptionRepository, Mockito.times(1)).deleteById("1");
  }

   @Test
    void shouldUpdateASubscription(){
      UpdateSubscriptionRequest request = new UpdateSubscriptionRequest(
              "hola@hola.com",
                "2");

       PlanEntity planEntity = new PlanEntity("2", "Premium", 20.0);

      SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
              "1",
              "ww@ww.com",
              planEntity);

       Mockito.when(planRepository.findById("2"))
               .thenReturn(Optional.of(planEntity));
       Mockito.when(subscriptionRepository.findById("1"))
               .thenReturn(java.util.Optional.of(subscriptionEntity));
        Mockito.when(subscriptionRepository.save(any(SubscriptionEntity.class)))
              .thenAnswer(i -> i.getArgument(0));

        subscriptionService.updateSubscription(request, "1");

        Mockito.verify(subscriptionRepository,
                Mockito.times(1)).save(any(SubscriptionEntity.class));


    assertEquals("hola@hola.com",subscriptionEntity.getCustomerEmail() );
    assertEquals("2", subscriptionEntity.getPlan().getId());

  }

  @Test
   void shouldReturnSubscriptionPage(){
      PlanEntity planEntity = new PlanEntity("1", "Premium", 20.0);

      SubscriptionEntity entity =
              new SubscriptionEntity("1", "hola@hola.com", planEntity);


      Sort sort = Sort.by("customerEmail").descending();
        Pageable pageable = PageRequest.of(0, 2, sort);

      Page<SubscriptionEntity> page = new PageImpl<>(List.of(entity),pageable,1);

        Mockito.when(subscriptionRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<Subscription> result = subscriptionService.getSubscriptionPage(0, 2,
                "customerEmail", "desc");

        assertEquals(1, result.getContent().size());
        assertEquals("hola@hola.com", result.getContent().get(0).getCustomerEmail());
  }

}




