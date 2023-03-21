package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Food;
import com.food.apnajalpaan.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class FoodService {
    @Autowired
    FoodRepository repository;

    @Autowired
    ImageService imageService;

    public Mono<Food> saveFood(Mono<Food> foodMono){
        return foodMono.flatMap(
                res -> {
                    return imageService.getByName(res.getFoodName()).flatMap(
                            val -> {
                                res.setImageId(val.getImageId());
                                return Mono.just(res);
                            }
                    );
                }
        ).flatMap(repository::insert);
    }

    public Mono<Food> updateFood(String foodId,Mono<Food> foodMono){
        return repository.findById(foodId)
                .flatMap(res -> {
                    return foodMono.flatMap(
                            x -> {
                                if(x.getFoodCost()!=null) res.setFoodCost(x.getFoodCost());
                                if(x.getFoodName()!=null) res.setFoodName(x.getFoodName());
                                if(x.getImageId()!=null) res.setImageId(x.getImageId());
                                if(x.getType()!=null) res.setType(x.getType());
                                if(x.getRating()!=null) res.setRating(x.getRating());
                                if(x.getIsAvailable()!=null) res.setIsAvailable(x.getIsAvailable());
                                if(x.getCategory()!=null) res.setCategory(x.getCategory());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<Void> deleteFood(String foodId){
        return repository.deleteById(foodId);
    }

    public Flux<Food> getAllFood() {
        return repository.findAll();
    }
    public Mono<Food> getFoodByFoodId(String foodId) {
        return repository.findById(foodId);
    }

    public Flux<Food> findAllById(List<String> ids){
        return repository.findAllById(ids);
    }

    // search food by food name and type of food
    public Flux<Food> getDataForSearch(String searchQuery){
        if(searchQuery.isEmpty()){
            return repository.findAll();
        }else {
            return repository.findDistinctFoodByFoodNameContainingIgnoreCaseOrTypeContainingIgnoreCase(searchQuery,searchQuery);
        }
    }

    public Flux<Food> getDataForSearchWithFilter(String searchQuery, String category) {
        return repository.findDistinctFoodByFoodNameContainingIgnoreCaseOrTypeContainingIgnoreCaseAndCategory(searchQuery,searchQuery,category);
    }
}
