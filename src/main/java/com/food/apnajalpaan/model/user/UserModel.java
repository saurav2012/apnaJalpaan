package com.food.apnajalpaan.model.user;

import com.food.apnajalpaan.model.Address;
import com.food.apnajalpaan.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserModel {
    @Id
    private String userId;

    @NotEmpty(message = "Username must be present")
    private String username;

    @NotEmpty
    @Size(min = 8,max=14,message = "Password must be between 8-14 characters!")
    private String password;

    @NotEmpty
    @Size(min = 3,message = "First name must be min of 3 characters!")
    private String firstName;

    private String lastName;
    private Long mobile;

    @Email(message = "Enter a valid email")
    @Indexed(unique = true)
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
