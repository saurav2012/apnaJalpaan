package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Status;
import com.food.apnajalpaan.model.user.UserModel;
import com.food.apnajalpaan.repository.CouponRepository;
import com.food.apnajalpaan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    @Lazy
    @Autowired
    CouponService couponService;
    @Autowired
    PasswordEncoder passwordEncoder;
    public Mono<UserModel> saveUser(Mono<UserModel> userModelMono){
        return userModelMono.flatMap(
                res -> {
                    res.setPassword(passwordEncoder.encode(res.getPassword()));
                    res.setDoj(LocalDate.now().toString());
                    return Mono.just(res);
                }
        ).flatMap(repository::insert);
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
                                if (x.getCouponIds()!=null) res.setCouponIds(x.getCouponIds());
                                if(x.getRole()!=null) res.setRole(x.getRole());
                                if(x.getIsActive()!=null) res.setIsActive(x.getIsActive());
                                if(x.getBirthdate()!=null) res.setBirthdate(x.getBirthdate());
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

    public Mono<UserModel> getUserByUsername(String username){
        return repository.findByUsername(username);
    }

    @Scheduled(cron = "${cron.expression.one-day}", zone = "${cron.timezone}")
    public Flux<Object> assignCouponIds(){
        List<String> couponCodeList = List.of("FLAT50","FLAT20","FLAT10","UPTO20");
        repository.findAll().flatMap(
            user -> {
                if(user.getDoj().equalsIgnoreCase(LocalDate.now().toString())) {
                    couponCodeList.forEach(
                        code -> {
                            couponService.findByCode(code).flatMap(
                                coupon -> {
                                    if(!user.getCouponIds().containsKey(coupon.getCouponId())){
                                        user.getCouponIds().put(coupon.getCouponId(),new Status(false,false,LocalDate.now().plusDays(60).toString()));
                                        System.out.println("after update");
                                    }
                                    System.out.println("in coupon");
                                    return Mono.just(coupon);
                                }
                            ).subscribe();
                        }
                    );
                }
                // set coupon is expired here!!
                return Mono.just(user);
            }
        ).flatMap(repository::save).subscribe();
        return Flux.empty();

    }

}
