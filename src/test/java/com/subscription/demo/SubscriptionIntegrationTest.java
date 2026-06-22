package com.subscription.demo;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SubscriptionIntegrationTest {

//    @Autowired
//    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads(){

    }

    @Test
    void shouldGetAllSubscriptions() throws Exception {

        mockMvc.perform(get("/subscriptions"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePlanAndSubscription() throws Exception{
        String planJson = """
                {
                    "name": "Premium",
                    "monthlyPrice": 20.0
                }
                """;

        String planResponse = mockMvc.perform(post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(planJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Premium"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String planId = JsonPath.read(planResponse, "$.id");

        String subscriptonJson= """
                {
                    "customerEmail": "integration@test.com",
                    "planId": "%s"
                }
                """.formatted(planId);

        mockMvc.perform(post("/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(subscriptonJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerEmail").value("integration@test.com"))
                .andExpect(jsonPath("$.plan.id").value(planId))
                .andExpect(jsonPath("$.plan.name").value("Premium"));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].customerEmail").value(hasItem("integration@test.com")));

    }
}
