package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Food;
import com.food.apnajalpaan.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FoodService {
    @Autowired
    FoodRepository repository;

    public Mono<Food> saveFood(Mono<Food> foodMono){
        return foodMono.flatMap(repository::insert);
    }

    public Mono<Food> updateFood(String foodId,Mono<Food> foodMono){
        return repository.findById(foodId)
                .flatMap(res -> {
                    return foodMono.flatMap(
                            x -> {
                                if(x.getFoodCost()!=null) res.setFoodCost(x.getFoodCost());
                                if(x.getFoodName()!=null) res.setFoodName(x.getFoodName());
                                if(x.getFoodImage()!=null) res.setFoodImage(x.getFoodImage());
                                if(x.getType()!=null) res.setType(x.getType());
                                if(x.getRating()!=null) res.setRating(x.getRating());
                                if(x.getIsAvailable()!=null) res.setIsAvailable(x.getIsAvailable());
                                if(x.getCategory()!=null) res.setCategory(x.getCategory());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<String> deleteFood(String foodId){
        repository.deleteById(foodId);
        return Mono.just("Food item deleted successfully");
    }

    public Flux<Food> getAllFood() {
        return repository.findAll();
    }
    public Mono<Food> getFoodByFoodId(String foodId) {
        return repository.findById(foodId);
    }

}
