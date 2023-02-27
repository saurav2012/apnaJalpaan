package com.food.apnajalpaanuser.model.user;

import com.food.apnajalpaanuser.model.*;

import java.util.List;

public class UserResponse extends UserModel{
    private List<Order> orders;
    private List<Coupon> coupons;
    private List<Reservation> reservations;
    private List<Notification> notifications;
}
