package com.food.apnajalpaanuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.food"})
@ComponentScan("com.food")
public class ApnajalpaanApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
	}

}
