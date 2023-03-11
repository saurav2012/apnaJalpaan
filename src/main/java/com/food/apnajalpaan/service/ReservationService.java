package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Reservation;
import com.food.apnajalpaan.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    ReservationRepository repository;
    public Mono<Reservation> saveReservation(Mono<Reservation> reservationMono){
        return reservationMono.flatMap(repository::insert);
    }

    public Mono<Reservation> updateReservation(String reservationId,Mono<Reservation> reservationMono){
        return repository.findById(reservationId)
                .flatMap(res -> {
                    return reservationMono.flatMap(
                            x -> {
                                if(x.getReservationType()!=null) res.setReservationType(x.getReservationType());
                                if(x.getIsConfirmed()!=null) res.setIsConfirmed(x.getIsConfirmed());
                                if(x.getNumOfGuest()!=null) res.setNumOfGuest(x.getNumOfGuest());
                                if(x.getReservationType()!=null) res.setReservationType(x.getReservationType());
                                if(x.getTime()!=null) res.setTime(x.getTime());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<Void> deleteReservation(String reservationId){
        return repository.deleteById(reservationId);
    }

    public Flux<Reservation> getAllReservation() {
        return repository.findAll();
    }
    public Mono<Reservation> getReservationByReservationId(String reservationId) {
        return repository.findById(reservationId);
    }

}
