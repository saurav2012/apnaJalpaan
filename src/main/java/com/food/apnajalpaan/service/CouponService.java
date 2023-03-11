package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Coupon;
import com.food.apnajalpaan.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CouponService {
    @Autowired
    CouponRepository repository;
    
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
                                if(x.getIsActivated()!=null) res.setIsActivated(x.getIsActivated());
                                if(x.getIsExpired()!=null) res.setIsExpired(x.getIsExpired());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
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
}
