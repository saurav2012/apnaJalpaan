package com.food.apnajalpaan;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.food.apnajalpaan.model.Role;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.food"})
@ComponentScan("com.food")
public class ApnajalpaanApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
	}

}
