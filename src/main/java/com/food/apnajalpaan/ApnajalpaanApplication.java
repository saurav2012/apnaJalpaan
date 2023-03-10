package com.food.apnajalpaan;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.food.apnajalpaan.model.Role;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.food.apnajalpaan"})
@ComponentScan("com.food")
public class ApnajalpaanApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(passwordEncoder.encode("test@123"));
//	}
}
