package com.food.apnajalpaan.controllers;

import com.food.apnajalpaan.model.Food;
import com.food.apnajalpaan.model.Image;
import com.food.apnajalpaan.model.Reservation;
import com.food.apnajalpaan.model.user.UserModel;
import com.food.apnajalpaan.service.ReservationService;
import com.food.apnajalpaan.service.UserService;
import com.food.apnajalpaan.service.FoodService;
import com.food.apnajalpaan.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApnaJalPaanController {
    @Autowired
    UserService userService;
    @Autowired
    FoodService foodService;
    @Autowired
    ImageService imageService;
    @Autowired
    ReservationService reservationService;

    // user endpoints
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Flux<UserModel> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/welcome")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Mono<String> get(){
        return Mono.just("Welcome");
    }

    @GetMapping("/user/{userId}")
    public Mono<UserModel> getUserByUserId(@PathVariable String userId){
        return userService.getUserByUserId(userId);
    }

    @PostMapping("/user/save")
    public Mono<UserModel> saveUser(@Valid @RequestBody Mono<UserModel> userModelRequest) {
        return userService.saveUser(userModelRequest);
    }

    @PutMapping("/user/update/{userId}")
    public Mono<UserModel> updateUser(@RequestBody Mono<UserModel> userModelRequest, @PathVariable String userId)
    {
        return userService.updateUser(userId,userModelRequest);
    }

    @DeleteMapping("/user/delete/{userId}")
    public Mono<String> deleteUser(@PathVariable String userId){
        return userService.deleteUser(userId);
    }

    //  Endpoint for food
    @GetMapping("/food")
    public Flux<Food> getAllFood(){
        return foodService.getAllFood();
    }

    @GetMapping("/food/{foodId}")
    public Mono<Food> getFoodByFoodId(@PathVariable String foodId){
        return foodService.getFoodByFoodId(foodId);
    }

    @PostMapping("/food/save")
    public Mono<Food> saveFood(@RequestBody Mono<Food> foodMono){
        return foodService.saveFood(foodMono);
    }

    @PutMapping("/food/update/{foodId}")
    public Mono<Food> updateFood(@RequestBody Mono<Food> foodMono, @PathVariable String foodId)
    {
        return foodService.updateFood(foodId,foodMono);
    }

    @DeleteMapping("/food/delete/{foodId}")
    public Mono<Void> deleteFood(@PathVariable String foodId){
        return foodService.deleteFood(foodId);
    }


    @PostMapping(value = "/image/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Image> saveImage(@RequestPart("file")Mono<FilePart> part) throws IOException {
        return part.flatMap(file -> {
            try {
                return imageService.saveImage(file);
            } catch (IOException | InterruptedException e ) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/image/{imageId}")
    public Mono<Image> getImageByImageId(@PathVariable String imageId){
        return imageService.downloadImage(imageId);
    }

    @GetMapping("/image")
    public Flux<Image> getAllImage(){
        return imageService.getAllImages();
    }

    // delete food by public Id
    @DeleteMapping("/image/delete/{publicId}")
    public Mono<Void> deleteImage(@PathVariable String publicId) throws IOException {
        return imageService.deleteImage(publicId);
    }

    //Reservation endpoints
    @PostMapping("/reservation/save")
    public Mono<Reservation> saveReservation(@RequestBody Mono<Reservation> reservationMono){
        return reservationService.saveReservation(reservationMono);
    }
    @GetMapping("/reservation")
    public Flux<Reservation> getAllReservation(){
        return reservationService.getAllReservation();
    }

    @GetMapping("/reservation/{reservationId}")
    public Mono<Reservation> getReservationByReservationId(@PathVariable String reservationId){
        return reservationService.getReservationByReservationId(reservationId);
    }

    @DeleteMapping("/reservation/delete/{reservationId}")
    public Mono<Void> deleteReservation(@PathVariable String reservationId){
        return reservationService.deleteReservation(reservationId);
    }

    @PutMapping("/reservation/update/{reservationId}")
    public Mono<Reservation> updateReservation(@RequestBody Mono<Reservation> reservationMono, @PathVariable String reservationId)
    {
        return reservationService.updateReservation(reservationId,reservationMono);
    }

}
