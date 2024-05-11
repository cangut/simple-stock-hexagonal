package com.example.can;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.can")
public class StockQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockQueryServiceApplication.class, args);
    }
}
