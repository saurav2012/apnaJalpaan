package com.food.apnajalpaan.controllers;

import com.food.apnajalpaan.model.*;
import com.food.apnajalpaan.model.user.UserModel;
import com.food.apnajalpaan.service.*;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.*;
import java.util.Map;

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
    @Autowired
    OrderService orderService;
    @Autowired
    CouponService couponService;
    @Autowired
    RestaurantService restaurantService;

    @Autowired
    PaymentService paymentService;

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
    @GetMapping("/user/{username}")
    public Mono<UserModel> getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
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

    @GetMapping("/food/search/{searchQuery}")
    public Flux<Food> searchForFood(@PathVariable String searchQuery){
        return foodService.getDataForSearch(searchQuery);
    }
    @GetMapping("/food/search/{searchQuery}/{category}")
    public Flux<Food> searchForFoodFilterCategory(@PathVariable String searchQuery,@PathVariable String category){
        return foodService.getDataForSearchWithFilterCategory(searchQuery,category);
    }
    @GetMapping("/food/search/{searchQuery}/rating/{rating}")
    public Flux<Food> searchForFoodFilterRating(@PathVariable String searchQuery,@PathVariable Double rating){
        return foodService.getDataForSearchWithFilterRating(searchQuery,rating);
    }
    @PostMapping("/food/rating/{foodId}")
    public Mono<Food> addRating(@RequestBody Mono<UserRating> userRating,@PathVariable String foodId){
        return foodService.addingUserRating(foodId,userRating);
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

    @PostMapping("/reservation/approve/{reservationId}")
    public Mono<Reservation> approveReservation(@RequestBody Mono<Reservation> reservationMono,@PathVariable String reservationId){
        return reservationService.approveReservationByAdmin(reservationId,reservationMono);
    }

    @PostMapping("/reservation/deny/{reservationId}")
    public Mono<Reservation> denyReservation(@RequestBody Mono<Reservation> reservationMono,@PathVariable String reservationId){
        return reservationService.denyReservationByAdmin(reservationId,reservationMono);
    }

    // endpoint for order
    @PostMapping("/order/save")
    public Mono<Order> saveOrder(@RequestBody Mono<Order> orderModelMono){
        return orderService.saveOrder(orderModelMono);
    }
    @GetMapping("/order")
    public Flux<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/order/{orderId}")
    public Mono<Order> getOrderByOrderId(@PathVariable String orderId){
        return orderService.getOrderByOrderId(orderId);
    }

    @DeleteMapping("/order/delete/{orderId}")
    public Mono<Void> deleteOrder(@PathVariable String orderId){
        return orderService.deleteOrder(orderId);
    }

    @PutMapping("/order/update/{orderId}")
    public Mono<Order> updateOrder(@RequestBody Mono<Order> orderModelMono, @PathVariable String orderId)
    {
        return orderService.updateOrder(orderId,orderModelMono);
    }

    //end point for coupon
    @PostMapping("/coupon/save")
    public Mono<Coupon> saveCoupon(@RequestBody Mono<Coupon> couponModelMono){
        return couponService.saveCoupon(couponModelMono);
    }
    @GetMapping("/coupon")
    public Flux<Coupon> getAllCoupon(){
        return couponService.getAllCoupon();
    }

    @GetMapping("/coupon/{couponId}")
    public Mono<Coupon> getCouponByCouponId(@PathVariable String couponId){
        return couponService.getCouponByCouponId(couponId);
    }

    @DeleteMapping("/coupon/delete/{couponId}")
    public Mono<Void> deleteCoupon(@PathVariable String couponId){
        return couponService.deleteCoupon(couponId);
    }

    @PutMapping("/coupon/update/{couponId}")
    public Mono<Coupon> updateCoupon(@RequestBody Mono<Coupon> couponModelMono, @PathVariable String couponId)
    {
        return couponService.updateCoupon(couponId,couponModelMono);
    }

    // endpoint for restaurant
    @PostMapping("/restaurant/save")
    public Mono<Restaurant> saveRestaurant(@RequestBody Mono<Restaurant> restaurantModelMono){
        return restaurantService.saveRestaurant(restaurantModelMono);
    }
    @GetMapping("/restaurant")
    public Flux<Restaurant> getAllRestaurant(){
        return restaurantService.getAllRestaurant();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public Mono<Restaurant> getRestaurantByRestaurantId(@PathVariable String restaurantId){
        return restaurantService.getRestaurantByRestaurantId(restaurantId);
    }

    @DeleteMapping("/restaurant/delete/{restaurantId}")
    public Mono<Void> deleteRestaurant(@PathVariable String restaurantId){
        return restaurantService.deleteRestaurant(restaurantId);
    }

    @PutMapping("/restaurant/update/{restaurantId}")
    public Mono<Restaurant> updateRestaurant(@RequestBody Mono<Restaurant> restaurantModelMono, @PathVariable String restaurantId)
    {
        return restaurantService.updateRestaurant(restaurantId,restaurantModelMono);
    }
    @PostMapping("/restaurant/review/{restaurantId}")
    public Mono<Restaurant> addReview(@RequestBody Mono<Review> reviewMono,@PathVariable String restaurantId){
        return restaurantService.addReview(restaurantId,reviewMono);
    }

    @PostMapping("/food/order/{userId}/{amount}")
    public Mono<Payment> createOrder(@PathVariable Double amount,@PathVariable String userId) throws RazorpayException {
        return paymentService.createOrder(amount,userId);
    }

    @PostMapping("/food/update/order")
    public Mono<Payment> updateOrder(@RequestBody Mono<Payment> paymentMono){
        return paymentService.updateOrder(paymentMono);
    }
}
