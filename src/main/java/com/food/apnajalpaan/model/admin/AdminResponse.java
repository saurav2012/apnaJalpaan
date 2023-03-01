package com.food.apnajalpaan.model.admin;

import com.food.apnajalpaan.model.Notification;
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
