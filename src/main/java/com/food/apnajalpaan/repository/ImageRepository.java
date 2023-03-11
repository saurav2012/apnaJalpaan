package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Image;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ImageRepository extends ReactiveMongoRepository<Image,String> {
    public Mono<Void> deleteByPublicId(String publicId);
    public Mono<Image> findByName(String name);
}
