package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Reservation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends ReactiveMongoRepository<Reservation,String> {
}
