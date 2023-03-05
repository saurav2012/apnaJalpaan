package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.user.UserModel;
import com.food.apnajalpaan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;
    public Mono<UserModel> saveUser(Mono<UserModel> userModelMono){
        return userModelMono.flatMap(repository::insert);
    }

    public Mono<UserModel> updateUser(String userId, Mono<UserModel> userModelMono){
        return repository.findById(userId)
                .flatMap(res -> {
                    return userModelMono.flatMap(
                            x -> {
                                if(x.getUsername()!=null) res.setUsername(x.getUsername());
                                if(x.getAge()!=null) res.setAge(x.getAge());
                                if(x.getEmail()!=null) res.setEmail(x.getEmail());
                                if(x.getGender()!=null) res.setGender(x.getGender());
                                if(x.getMobile()!=null) res.setMobile(x.getMobile());
                                if(x.getPassword()!=null) res.setPassword(x.getPassword());
                                if(x.getFirstName()!=null) res.setFirstName(x.getFirstName());
                                if(x.getLastName()!=null) res.setLastName(x.getLastName());
                                if(x.getAddress()!=null) res.setAddress(x.getAddress());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<String> deleteUser(String userId){
        repository.deleteById(userId);
        return Mono.just("user is deleted successfully");
    }

    public Flux<UserModel> getAllUser() {
        return repository.findAll();
    }
    public Mono<UserModel> getUserByUserId(String userId) {
        return repository.findById(userId);
    }

}
