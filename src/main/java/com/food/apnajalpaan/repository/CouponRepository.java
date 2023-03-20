package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Coupon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CouponRepository extends ReactiveMongoRepository<Coupon,String> {
    Mono<Coupon> findByCode(String code);
}
