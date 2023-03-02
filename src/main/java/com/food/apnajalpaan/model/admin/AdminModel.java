package com.food.apnajalpaan.model.admin;

import com.food.apnajalpaan.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private Long mobile;
    private String email;
    private Integer age;
    private String gender;
    private Address address;
}
