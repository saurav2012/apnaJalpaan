package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Order;
import com.food.apnajalpaan.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository repository;

    
    public Mono<Order> saveOrder(Mono<Order> orderMono){
        return orderMono.flatMap(repository::insert);
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