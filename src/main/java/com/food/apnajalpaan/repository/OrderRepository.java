package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order,String> {
}
