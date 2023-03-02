package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Food;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends ReactiveMongoRepository<Food,String> {
}
