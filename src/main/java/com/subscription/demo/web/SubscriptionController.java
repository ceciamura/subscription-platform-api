package com.subscription.demo.web;

import com.subscription.demo.application.SubscriptionService;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.mapper.SubscriptionResponseMapper;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import com.subscription.demo.web.response.SubscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Create a new subscription")
    @PostMapping("/subscriptions")
    public SubscriptionResponse newSubscription(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     description = "New Subscription")
                                                @Valid @RequestBody CreateSubscriptionRequest subscription){
        Subscription newSubscription = subscriptionService.newSubscription(subscription);

        return SubscriptionResponseMapper.toResponse(newSubscription);

    }
    @Operation(summary = "Get all subscriptions")
    @GetMapping("/subscriptions")
    public List<SubscriptionResponse> getSubscriptions(){
        List<Subscription> subscriptionList = subscriptionService.getSubscriptions();
        return subscriptionList.stream()
                .map(SubscriptionResponseMapper::toResponse).toList();
    }

    @Operation(summary = "Get a subscription by ID")
    @GetMapping("/subscriptions/{id}")
    public SubscriptionResponse getSubscriptionById(@Parameter(description = "Subscription ID")
                                                    @PathVariable(name = "id") String id){

        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return SubscriptionResponseMapper.toResponse(subscription);
    }
    @Operation(summary = "Delete a subscription by ID")
    @DeleteMapping("/subscriptions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscriptionById(@Parameter(description = "Subscription ID")
                                       @PathVariable(name = "id") String id){
         subscriptionService.deleteSubscriptionById(id);
    }
    @Operation(summary = "Update a subscription by ID")
    @PutMapping("/subscriptions/{id}")
    public SubscriptionResponse updateSubscription(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                        description = "Subscription details to modify")
                                                   @Valid @RequestBody UpdateSubscriptionRequest request,
                                                   @Parameter(description = "Subscription ID")
                                                   @PathVariable(name = "id") String id){
        Subscription subscription = subscriptionService.updateSubscription(request, id);

        return SubscriptionResponseMapper.toResponse(subscription);
    }

    @GetMapping("/subscriptions/page")
    public Page<SubscriptionResponse> getSubscriptionPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "customerEmail") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        return subscriptionService.getSubscriptionPage(page, size, sortBy, direction)
                .map(SubscriptionResponseMapper::toResponse);
    }
}
