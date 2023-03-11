package com.food.apnajalpaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalTime;

@SpringBootApplication(scanBasePackages = {"com.food.apnajalpaan"})
@ComponentScan("com.food")
public class ApnajalpaanApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
	}
}
