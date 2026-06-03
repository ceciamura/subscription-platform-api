package com.subscription.demo.web.mapper;

import com.subscription.demo.domain.Plan;

import com.subscription.demo.web.response.PlanResponse;


public class PlanResponseMapper {

    public static PlanResponse toResponse(Plan plan){
            return new PlanResponse(
                  plan.getId(),
                  plan.getName(),
                  plan.getMonthlyPrice());

    }
}
