package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {
    Mono<Payment> findByOrderId(String orderId);
}
