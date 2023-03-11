package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Restaurant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ReactiveMongoRepository<Restaurant, String> {
}
