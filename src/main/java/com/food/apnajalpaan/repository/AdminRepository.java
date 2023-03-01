package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.admin.AdminModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends ReactiveMongoRepository<AdminModel,String> {
}
