package com.food.apnajalpaan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {
    @Id
    private String orderId;
    @NonNull
    private String userId;
    private Double totalAmount = 0.0;
    private Double discount = 0.0;
    private Double taxOrCharges = 0.0;
    private Double netAmount = 0.0;
    private Map<String,Integer> foods;
    private String date;
    private String couponApplied;
    private String time;
    private String deliveryOrTakeAway;
}
