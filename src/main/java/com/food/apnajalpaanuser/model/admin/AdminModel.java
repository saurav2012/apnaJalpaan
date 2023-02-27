package com.food.apnajalpaanuser.model.admin;

import com.food.apnajalpaanuser.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "admin")
public class AdminModel {
    @Id
    private String adminId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer mobile;
    private String email;
    private Integer age;
    private String gender;
    private Address addressModel;
    private List<String> notificationIds;
}
