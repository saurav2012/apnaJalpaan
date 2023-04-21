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
@Document(collection = "food")
public class Food {
    @Id
    private String foodId;
    private String foodName;
    private Double foodCost;
    private String category;
    private String type;
    private Double rating;
    private Map<String,Double> userRating;
    private Boolean isAvailable;
    private String imageId;
    private String imageUrl;
    private String description;
}
