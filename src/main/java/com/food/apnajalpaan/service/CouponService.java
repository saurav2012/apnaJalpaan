package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Coupon;
import com.food.apnajalpaan.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    CouponRepository repository;
    @Autowired
    UserService userService;

    static final String FLAT50 = "FLAT50";
    static final String FLAT30 = "FLAT30";
    static final String FLAT20 = "FLAT20";
    
    public Mono<Coupon> saveCoupon(Mono<Coupon> couponMono){
        return couponMono.flatMap(repository::insert);
    }

    public Mono<Coupon> updateCoupon(String couponId, Mono<Coupon> couponMono){
        return repository.findById(couponId)
                .flatMap(res -> {
                    return couponMono.flatMap(
                            x -> {
                                if(x.getCode()!=null) res.setCode(x.getCode());
                                if(x.getMessage()!=null) res.setMessage(x.getMessage());
                                if(x.getName()!=null) res.setMessage(x.getMessage());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }
    public Mono<Coupon> findByCode(String code){
        return repository.findByCode(code);
    }

    public Mono<Void> deleteCoupon(String couponId){
        return repository.deleteById(couponId);
    }

    public Flux<Coupon> getAllCoupon() {
        return repository.findAll();
    }

    public Mono<Coupon> getCouponByCouponId(String couponId) {
        return repository.findById(couponId);
    }
    // assigning coupons to user

}
