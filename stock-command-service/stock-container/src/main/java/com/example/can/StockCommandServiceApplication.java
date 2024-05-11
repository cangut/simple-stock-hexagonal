package com.example.can;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.can")
public class StockCommandServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockCommandServiceApplication.class, args);
    }
}