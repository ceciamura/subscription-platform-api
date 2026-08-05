package com.subscription.demo.application;

import com.subscription.demo.infrastucture.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    public void shouldGenerateToken(){

        String token = jwtService.generateToken("admin");
        System.out.println(token);

        assertNotNull(token);

    }

    @Test
    public void shouldGenerateValidToken(){
        String token = jwtService.generateToken("admin");
        String username = jwtService.getUserName(token);

        assertEquals(username,"admin");
        assertTrue(jwtService.isValidToken(token, "admin"));
        assertFalse(jwtService.isValidToken(token, "maria"));
    }
}
