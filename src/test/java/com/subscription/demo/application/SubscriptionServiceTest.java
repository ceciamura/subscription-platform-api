package com.subscription.demo.application;

import com.subscription.demo.domain.Subscription;
import com.subscription.demo.infrastucture.persistence.SubscriptionEntity;
import com.subscription.demo.infrastucture.persistence.SubscriptionRepository;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class SubscriptionServiceTest {


    private final SubscriptionRepository subscriptionRepository =
            Mockito.mock(SubscriptionRepository.class);

    private final SubscriptionService subscriptionService =
            new SubscriptionService(subscriptionRepository);


    @Test
    void shouldCreateANewSubscription() {

        CreateSubscriptionRequest request =
                new CreateSubscriptionRequest(
                        "aaa@aa.com",
                        11.0);

        Mockito.when(subscriptionRepository.save(any())).
                thenAnswer(i -> i.getArgument(0));

        var result = subscriptionService.newSubscription(request);

        assertNotNull(result);
        assertEquals("aaa@aa.com", result.getCustomerEmail());
        assertEquals(11.0, result.getMonthlyPrice());

    }

    @Test
    void shouldReturnAllSubscriptions(){
        //given
       SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
               "1",
               "ww@ww.com",
               20.0);
         List<SubscriptionEntity> entityList = List.of(subscriptionEntity);

         Mockito.when(subscriptionRepository.findAll()).thenReturn(entityList);

         List<Subscription> list = subscriptionService.getSubscriptions();

         assertEquals(list.size(), entityList.size());
         assertEquals("1", list.get(0).getId());
        assertEquals("ww@ww.com", list.get(0).getCustomerEmail());
        assertEquals(20.0, list.get(0).getMonthlyPrice());
    }

    @Test
    void shouldReturnSubscriptionById(){
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
                "1",
                "ww@ww.com",
                20.0);

        Mockito.when(subscriptionRepository.findById(any()))
                .thenReturn((Optional.of(subscriptionEntity)));

        Subscription subscriptionFound = subscriptionService.
            getSubscriptionById(subscriptionEntity.getId());

        assertNotNull(subscriptionFound);
        assertEquals("1",subscriptionFound.getId() );
        assertEquals("ww@ww.com",subscriptionFound.getCustomerEmail() );
        assertEquals(20.0,subscriptionFound.getMonthlyPrice() );
    }

    @Test
  void shouldDeleteASubscriptionById(){
      SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
              "1",
              "ww@ww.com",
              20.0);


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
                3.0);

      SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
              "1",
              "ww@ww.com",
              20.0);


       Mockito.when(subscriptionRepository.findById("1"))
               .thenReturn(java.util.Optional.of(subscriptionEntity));
        Mockito.when(subscriptionRepository.save(any(SubscriptionEntity.class)))
              .thenAnswer(i -> i.getArgument(0));

        subscriptionService.updateSubscription(request, "1");

        Mockito.verify(subscriptionRepository,
                Mockito.times(1)).save(any(SubscriptionEntity.class));


    assertEquals("hola@hola.com",subscriptionEntity.getCustomerEmail() );
    assertEquals(3.0, subscriptionEntity.getMonthlyPrice());

  }

}




