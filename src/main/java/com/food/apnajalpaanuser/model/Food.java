package com.food.apnajalpaanuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "food")
public class Food {
    @Id
    private String foodId;
    private String foodName;
    private String foodImage;
    private Double foodCost;
    private String category;
    private String type;
    private Double rating;
    private Boolean isAvailable;
}
