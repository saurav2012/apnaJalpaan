package com.food.apnajalpaan;

import com.food.apnajalpaan.utility.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication(scanBasePackages = {"com.food.apnajalpaan"})
@ComponentScan("com.food")
public class ApnajalpaanApplication {
	@Autowired
	EmailHelper emailHelper;
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
	}
//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//
//		emailHelper.sendSimpleEmail("20121998saurav@gmail.com",
//				"This is Email Body with Attachment...",
//				"This email has attachment");
//
//	}
}
