package com.food.apnajalpaanuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String locality;
    private String city;
    private String state;
    private String country;
    private Integer pincode;
}
