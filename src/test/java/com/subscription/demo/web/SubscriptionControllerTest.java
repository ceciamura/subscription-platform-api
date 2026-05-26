package com.subscription.demo.web;

import com.subscription.demo.application.SubscriptionService;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionService subscriptionService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldCreateANewSubscription() throws Exception {
        CreateSubscriptionRequest request = new CreateSubscriptionRequest(
                "aa@aa.com", 10.0);

       Subscription subscription = new Subscription(
                "1","aa@aa.com" , 10.0);

        Mockito.when(subscriptionService.newSubscription(any()))
                .thenReturn(subscription);

        mockMvc.perform(
                post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.customerEmail").value("aa@aa.com"))
                .andExpect(jsonPath("$.monthlyPrice").value(10.0));
    }

    @Test
    void shouldReturnAllSubscriptions() throws Exception {
        Subscription subscription = new Subscription(
                "1","aa@aa.com" , 10.0);

        List<Subscription> list = List.of(subscription);

        Mockito.when(subscriptionService.getSubscriptions())
                .thenReturn(list);

        mockMvc.perform(
                get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].customerEmail").value("aa@aa.com"))
                .andExpect( jsonPath("$[0].monthlyPrice", is(10.0)));


    }

    @Test
    void shouldReturnASubscriptionById() throws Exception {

        Subscription subscription = new Subscription(
                "1","aa@aa.com" , 10.0);

        Mockito.when(subscriptionService.getSubscriptionById("1")).
               thenReturn(subscription);


        mockMvc.perform(
                        get("/subscriptions/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.customerEmail").value("aa@aa.com"))
                .andExpect(jsonPath("$.monthlyPrice").value(10.0));
    }

    @Test
    void shouldDeleteSubscriptionById() throws Exception {

        Mockito.doNothing().when(subscriptionService)
                .deleteSubscriptionById("1");

        mockMvc.perform(
                        delete("/subscriptions/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(subscriptionService, Mockito.times(1)).deleteSubscriptionById("1");
    }

    @Test
    void shouldUpdateSubscription() throws Exception {
        UpdateSubscriptionRequest request = new UpdateSubscriptionRequest(
                "aa@aa.com", 50.0);

        Mockito.when(subscriptionService.updateSubscription(Mockito.any(UpdateSubscriptionRequest.class), Mockito.eq("1")))
                .thenReturn(new Subscription("1","aa@aa.com", 50.0 ));


        mockMvc.perform(
                        put("/subscriptions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.customerEmail").value("aa@aa.com"))
                .andExpect(jsonPath("$.monthlyPrice").value(50.0));
    }
}