package com.food.apnajalpaan.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.apnajalpaan.model.Address;
import com.food.apnajalpaan.model.Role;
import com.food.apnajalpaan.model.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "user")
public class UserModel implements UserDetails {
    @Id
    private String userId;

    @NotEmpty(message = "Username must be present")
    private String username;

    @NotEmpty(message = "Enter your password")
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
    private String anniversary;
    private String doj = LocalDate.now().toString();
    private String profileImageId;
    private Address address;
    private Map<String, Status> couponIds;
    private Role role;
    private Boolean isActive;
    Collection authoritiesList;
    public UserModel(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authoritiesList = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritiesList;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
