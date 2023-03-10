package com.food.apnajalpaan.model.user;

import com.food.apnajalpaan.model.Address;
import com.food.apnajalpaan.model.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
