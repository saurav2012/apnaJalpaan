package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.user.UserModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserModel,String> {
}
