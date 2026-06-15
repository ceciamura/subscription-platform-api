package com.subscription.demo.web;

import com.subscription.demo.application.PlanService;
import com.subscription.demo.domain.Plan;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.request.CreatePlanRequest;
import com.subscription.demo.web.request.UpdatePlanRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import tools.jackson.databind.ObjectMapper;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlanService planService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAnewPlan() throws Exception {
        CreatePlanRequest request = new CreatePlanRequest(
                "aa", 20.0);

        Plan plan = new Plan("1", "aa", 20.0);

        Mockito.when(planService.createNewPlan(any(CreatePlanRequest.class))).thenReturn(plan);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("aa"))
                .andExpect(jsonPath("$.monthlyPrice").value(20.0));
    }


    @Test
    public void shouldReturnAllPlans() throws Exception {
        List<Plan> planList = List.of(new Plan("1", "aa", 10.0));

        Mockito.when(planService.getPlans()).thenReturn(planList);

        mockMvc.perform(
                get("/plans")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("aa"))
                .andExpect(jsonPath("$[0].monthlyPrice").value(10.0));

    }

    @Test
    public void  shouldReturnAPlanById() throws Exception {

        Plan plan = new Plan("1", "aa", 20.0);
        Mockito.when(planService.getPlanById("1")).thenReturn(plan);

        mockMvc.perform(
                        get("/plans/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("aa"))
                .andExpect(jsonPath("$.monthlyPrice").value(20.0));

    }

    @Test
    public void shouldDeleteAPlanById() throws Exception {
        Mockito.doNothing().when(planService).deletePlanById("1");
        mockMvc.perform(
                        delete("/plans/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(planService, Mockito.times(1)).deletePlanById("1");
    }

    @Test
    public void shouldUpdateAPlanById() throws Exception {
        UpdatePlanRequest request = new UpdatePlanRequest("aa", 10.0);

        Plan plan = new Plan("1", "aa", 10.0);
        
        Mockito.when(planService.updatePlan(any(UpdatePlanRequest.class
        ),  Mockito.eq("1"))).thenReturn(plan);

        mockMvc.perform(
                put("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(plan.getId()))
                .andExpect(jsonPath("$.name").value(plan.getName()))
                .andExpect(jsonPath("$.monthlyPrice").value(plan.getMonthlyPrice()));


    }

    @Test
    public void shouldReturnSubscriptionListByIdPlan() throws  Exception{
        List<Subscription> subscriptionList = List.of(new Subscription(
                "1",
                "aa",
                new Plan("2", "bb", 10.0)));

        Mockito.when(planService.getSubscriptionByIdPlan(any())).thenReturn(subscriptionList);

        mockMvc.perform(
                get("/plans/2/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value("1"))
                .andExpect(jsonPath("[0].customerEmail").value("aa"))
                .andExpect(jsonPath("[0].plan.id").value("2"))
                .andExpect(jsonPath("[0].plan.name").value("bb"))
                .andExpect(jsonPath("[0].plan.monthlyPrice").value(10.0));
    }
    }




