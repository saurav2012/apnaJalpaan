package com.food.apnajalpaanuser.model.admin;

import com.food.apnajalpaanuser.model.Notification;
import com.food.apnajalpaanuser.model.Reservation;
import com.food.apnajalpaanuser.model.Food;
import com.food.apnajalpaanuser.model.Order;
import com.food.apnajalpaanuser.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse extends AdminModel{
    private List<Notification> notifications;
}
