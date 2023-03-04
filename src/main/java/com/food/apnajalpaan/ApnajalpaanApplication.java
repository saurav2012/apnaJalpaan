package com.food.apnajalpaan;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.food"})
@ComponentScan("com.food")
public class ApnajalpaanApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApnajalpaanApplication.class, args);
//		Dotenv dotenv = Dotenv.load();
//		Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
//		cloudinary.config.secure = true;
//		System.out.println(
//				cloudinary.config.cloudName);
//
//		try {
//
//			// Upload the image
//			Map params1 = ObjectUtils.asMap(
//					"use_filename", true,
//					"unique_filename", false,
//					"overwrite", true
//			);
//			System.out.println(
//					cloudinary.uploader().upload("C:\\Users\\saukumar38\\Documents\\Personal\\ApnaJalpaanProject\\Images\\cake.png", params1));
//
//
//			// Get the asset details
//			Map params2 = ObjectUtils.asMap(
//					"quality_analysis", true
//			);
//			System.out.println("saurav"+
//					cloudinary.api().resource("cake", params2).apiRateLimit());
//
//
//			// Create the image tag with the transformed image and log it to the console
//			System.out.println(
//					cloudinary.url()
//							.generate("cake"));
//			// The code above generates an HTML image tag similar to the following:
//			//  <img src='https://res.cloudinary.com/demo/image/upload/b_auto:predominant,c_pad,h_400,w_300/coffee_cup' height='400' width='300'/>
//
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
	}

}
