package com.food.apnajalpaan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurant")
public class Restaurant {
    @Id
    private String restaurantId;
    private String name;
    private String description;
    private String openTiming;
    private String closeTiming;
    private String location;
    private String email;
    private Long contact;
    private Map<String,String> review;
}
