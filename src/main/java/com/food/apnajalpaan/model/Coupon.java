package com.food.apnajalpaan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coupon")
public class Coupon {
    @Id
    private String couponId;
    private String name;
    private String code;
    private String message;
    private String expDate;
    private Boolean isActivated;
    private Boolean isExpired;
}
