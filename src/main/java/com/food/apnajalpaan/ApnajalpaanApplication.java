package com.food.apnajalpaan;

import com.food.apnajalpaan.utility.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication(scanBasePackages = {"com.food.apnajalpaan"})
@ComponentScan("com.food")
@EnableScheduling
public class ApnajalpaanApplication {
	@Autowired
	EmailHelper emailHelper;
	@Bean
	public ScheduledExecutorService scheduledExecutorService() {
		return Executors.newScheduledThreadPool(15);
	}
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
	}

}
