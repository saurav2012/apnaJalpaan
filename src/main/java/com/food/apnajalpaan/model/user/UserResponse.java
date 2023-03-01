package com.food.apnajalpaan.model.user;

import com.food.apnajalpaan.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends UserModel{
    private List<Order> orders;
    private List<Coupon> coupons;
    private List<Reservation> reservations;
    private List<Notification> notifications;
}
