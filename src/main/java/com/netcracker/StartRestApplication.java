package com.netcracker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Store API", version = "1.0", description = "Book Store web service"))
public class StartRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartRestApplication.class, args);
    }
}
