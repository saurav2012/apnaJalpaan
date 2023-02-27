package com.food.apnajalpaanuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurant")
public class Restaurant {
    @Id
    private String hotel_id;
    private String name;
    private String description;
    private LocalTime openTiming;
    private LocalTime closeTiming;
    private String location;
    private String email;
    private Integer phoneNo;
}
