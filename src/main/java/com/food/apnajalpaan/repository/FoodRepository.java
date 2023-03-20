package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Food;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FoodRepository extends ReactiveMongoRepository<Food,String> {
    @Query(value = "select * from food like %$searchQuery$% in(FoodName,Type)")
    Flux<Food> findByFoodNameContainingIgnoreCase(String searchQuery);
    Flux<Food> findByFoodNameAndType(String searchQuery);
}
