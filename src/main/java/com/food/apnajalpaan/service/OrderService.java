package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Order;
import com.food.apnajalpaan.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    FoodService foodService;

    
    public Mono<Order> saveOrder(Mono<Order> orderMono){
        Double[] amt = {0.0};
        return orderMono.flatMap(
                order -> {
                    order.getFoodIds().forEach((foodId) -> {
                        foodService.getFoodByFoodId(foodId).flatMap(
                                food -> {
                                    Double x = order.getAmount();
                                    amt[0] = x + food.getFoodCost();
                                    order.setAmount(amt[0]);
                                    System.out.println("saurav"+amt[0]+" "+ food.getFoodCost());
                                    return Mono.just(food);
                                }
                        ).subscribe();
                    });
                    order.setAmount(amt[0]);
                    order.setDate(LocalDate.now().toString());
                    return Mono.just(order);
                }
        ).flatMap(repository::insert);
    }

    public Mono<Order> updateOrder(String orderId, Mono<Order> orderMono){
        return repository.findById(orderId)
            .flatMap(res -> {
                return orderMono.flatMap(
                    x -> {
                        if(x.getAmount()!=null) res.setAmount(x.getAmount());
                        if(x.getDate()!=null) res.setDate(x.getDate());
                        if(x.getFoodIds()!=null) res.setFoodIds(x.getFoodIds());
                        return Mono.just(res);
                    });
            })
            .flatMap(repository::save);
    }

    public Mono<Void> deleteOrder(String orderId){
        return repository.deleteById(orderId);
    }

    public Flux<Order> getAllOrder() {
        return repository.findAll();
    }

    public Mono<Order> getOrderByOrderId(String orderId) {
        return repository.findById(orderId);
    }
}