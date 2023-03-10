package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.user.UserModel;
import com.food.apnajalpaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(user -> new UserModel(user.getUsername(),user.getPassword(),List.of(new SimpleGrantedAuthority(user.getRole().toString()))));
    }
}

