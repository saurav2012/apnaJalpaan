package com.food.apnajalpaan.model.user;

import com.food.apnajalpaan.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends UserModel {
    private List<Notification> notifications;
}
