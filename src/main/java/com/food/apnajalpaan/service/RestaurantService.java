package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Restaurant;
import com.food.apnajalpaan.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository repository;

    public Mono<Restaurant> saveRestaurant(Mono<Restaurant> restaurantMono){
        return restaurantMono.flatMap(repository::insert);
    }

    public Mono<Restaurant> updateRestaurant(String restaurantId, Mono<Restaurant> restaurantMono){
        return repository.findById(restaurantId)
                .flatMap(res -> {
                    return restaurantMono.flatMap(
                            x -> {
                                if(x.getName()!=null) res.setName(x.getName());
                                if(x.getDescription()!=null) res.setDescription(x.getDescription());
                                if(x.getEmail()!=null) res.setEmail(x.getEmail());
                                if(x.getLocation()!=null) res.setLocation(x.getLocation());
                                if(x.getCloseTiming()!=null) res.setCloseTiming(x.getCloseTiming());
                                if(x.getOpenTiming()!=null) res.setOpenTiming(x.getOpenTiming());
                                if(x.getPhoneNo()!=null) res.setPhoneNo(x.getPhoneNo());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<Void> deleteRestaurant(String restaurantId){
        return repository.deleteById(restaurantId);
    }

    public Flux<Restaurant> getAllRestaurant() {
        return repository.findAll();
    }

    public Mono<Restaurant> getRestaurantByRestaurantId(String restaurantId) {
        return repository.findById(restaurantId);
    }
}
