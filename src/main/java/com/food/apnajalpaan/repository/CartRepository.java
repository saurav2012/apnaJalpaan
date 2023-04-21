package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.CartItem;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartRepository extends ReactiveMongoRepository<CartItem,String> {
    Mono<Void> deleteByUserId(String userId);
}
