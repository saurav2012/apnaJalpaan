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

    public Mono<Order> saveOrder(Mono<Order> orderMono) {
        return orderMono.flatMap(repository::insert);
    }

    public Mono<Order> updateOrder(String orderId, Mono<Order> orderMono){
        return repository.findById(orderId)
            .flatMap(res -> orderMono.flatMap(
                x -> {
                    if(x.getNetAmount()!=null) res.setNetAmount(x.getNetAmount());
                    if(x.getDiscount()!=null) res.setDiscount(x.getDiscount());
                    if(x.getTotalAmount()!=null) res.setTotalAmount(x.getTotalAmount());
                    if (x.getTaxOrCharges()!=null) res.setTaxOrCharges(x.getTaxOrCharges());
                    if(x.getCouponApplied()!=null) res.setCouponApplied(x.getCouponApplied());
                    if(x.getDate()!=null) res.setDate(x.getDate());
                    if(x.getFoods()!=null) res.setFoods(x.getFoods());
                    if (x.getTime()!=null) res.setTime(x.getTime());
                    if (x.getDeliveryOrTakeAway()!=null) res.setDeliveryOrTakeAway(x.getDeliveryOrTakeAway());
                    if(x.getIsPaid()!=null) res.setIsPaid(x.getIsPaid());
                    return Mono.just(res);
                }))
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