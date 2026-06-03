package com.subscription.demo.web;

import com.subscription.demo.application.PlanService;
import com.subscription.demo.domain.Plan;
import com.subscription.demo.web.mapper.PlanResponseMapper;
import com.subscription.demo.web.request.CreatePlanRequest;
import com.subscription.demo.web.request.UpdatePlanRequest;
import com.subscription.demo.web.response.PlanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlanController {
    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @Operation(description = "Create a new plan")
    @PostMapping("/plans")
    public PlanResponse newPlan(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New Plan")
            @RequestBody CreatePlanRequest createPlanRequest){

        Plan plan = planService.createNewPlan(createPlanRequest);

        return PlanResponseMapper.toResponse(plan);
    }

    @Operation(description = "Get all plans")
    @GetMapping("/plans")
    public List<PlanResponse> getallPlans(){

        List<Plan> planList = planService.getPlans();

        return planList.stream()
                .map(PlanResponseMapper::toResponse)
                .toList();
    }

    @Operation(description = "Get a plan by Id")
    @GetMapping("/plans/{id}")
    public PlanResponse getPlanById(
            @Parameter(description = "Plan ID")
            @PathVariable(name = "id") String id){

        Plan planFound = planService.getPlanById(id);

        return PlanResponseMapper.toResponse(planFound);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete a plan by Id")
    @DeleteMapping("/plans/{id}")
    public void deletePlanById(@Parameter(description = "Plan id")
                               @PathVariable(name = "id") String id){

        planService.deletePlanById(id);

    }

    @PutMapping("/plans/{id}")
    public PlanResponse updatePlan(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New Plan")
            @Valid
            @RequestBody UpdatePlanRequest request,
            @Parameter(description = "Plan id")
            @PathVariable(name = "id")String id){


        Plan plan = planService.updatePlan(request, id);

        return PlanResponseMapper.toResponse(plan);
    }


}
