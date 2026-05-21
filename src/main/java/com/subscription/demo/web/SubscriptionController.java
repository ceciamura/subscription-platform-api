package com.subscription.demo.web;

import com.subscription.demo.application.SubscriptionService;
import com.subscription.demo.domain.Subscription;
import com.subscription.demo.web.mapper.ResponseMapper;
import com.subscription.demo.web.request.CreateSubscriptionRequest;
import com.subscription.demo.web.request.UpdateSubscriptionRequest;
import com.subscription.demo.web.response.SubscriptionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    public SubscriptionController(SubscriptionService subscriptonService) {
        this.subscriptionService = subscriptonService;
    }


    @PostMapping("/subscriptions")
    public SubscriptionResponse newSubscription(@Valid @RequestBody CreateSubscriptionRequest subscription){
        Subscription newSubscription = subscriptionService.newSubscription(subscription);

        return ResponseMapper.toResponse(newSubscription);

    }

    @GetMapping("/subscriptions")
    public List<SubscriptionResponse> getSubscriptions(){
        List<Subscription> subscriptionList = subscriptionService.getSubscriptions();
        return subscriptionList.stream()
                .map(ResponseMapper::toResponse).toList();
    }

    @GetMapping("/subscriptions/{id}")
    public SubscriptionResponse getSubscriptionById(@PathVariable(name = "id") String id){

        Subscription subscription = subscriptionService.getSubscriptionById(id);

        return ResponseMapper.toResponse(subscription);
    }

    @DeleteMapping("/subscriptions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscriptionById(@PathVariable(name = "id") String id){
         subscriptionService.deleteSubscriptionById(id);
    }

    @PutMapping("/subscription/{id}")
    public SubscriptionResponse updateSubscription(@Valid @RequestBody UpdateSubscriptionRequest request, @PathVariable(name = "id") String id){
        Subscription subscription = subscriptionService.updateSubscription(request, id);

        return ResponseMapper.toResponse(subscription);
    }
}
