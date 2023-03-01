package com.food.apnajalpaan.model.user;

import com.food.apnajalpaan.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserModel {
    @Id
    private String userId;
    private String name;
    private String password;
    private String firstName;
    private String lastName;
    private Integer phoneNo;
    private LocalDate birthday;
    private String email;
    private String aniversary;
    private String profileImg;
    private Address address;
    private List<String> couponIds;      // it cannot be user specific
}