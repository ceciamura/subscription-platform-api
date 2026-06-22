package com.subscription.demo.infrastucture.persistence.mapper;

import com.subscription.demo.domain.Plan;
import com.subscription.demo.infrastucture.persistence.PlanEntity;

public class PlanEntityMapper {

    public static Plan toDomain(PlanEntity planEntity){
        return new Plan(planEntity.getId(),
                planEntity.getName(),
                planEntity.getMonthlyPlan());
    }

    public static PlanEntity toEntity(Plan plan){
        return new PlanEntity(plan.getId(),
                              plan.getName(),
                              plan.getMonthlyPrice());
    }
}
