package com.food.apnajalpaan.model.user;

import com.food.apnajalpaan.model.Address;
import com.food.apnajalpaan.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserModel {
    @Id
    private String userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Long mobile;
    private String email;
    private Integer age;
    private String gender;
    private String birthdate;
    private String aniversary;
    private String profileImageId;
    private Address address;
    private List<String> couponIds;
    private Role role;
}
