package com.mjutarzan.tarzan.domain.test;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CheckVariable {
    @PostConstruct
    public void init() {
        System.out.println("DB URL: " + System.getenv("DB_URL"));
        System.out.println("DB Username: " + System.getenv("DB_USERNAME"));
        System.out.println("DB Password: " + System.getenv("DB_PASSWORD"));
    }
}
