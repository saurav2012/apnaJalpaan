package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Coupon;
import com.food.apnajalpaan.model.Reservation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReservationRepository extends ReactiveMongoRepository<Reservation,String> {
    Flux<Reservation> findByUsername(String username);
}
