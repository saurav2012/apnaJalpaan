package com.food.apnajalpaan.controllers;

import com.food.apnajalpaan.model.Food;
import com.food.apnajalpaan.model.admin.AdminModel;
import com.food.apnajalpaan.service.AdminService;
import com.food.apnajalpaan.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
public class ApnaJalPaanController {
    @Autowired
    AdminService adminService;

    // admin endpoints
    @Autowired
    FoodService foodService;

    @GetMapping("/admin")
    public Flux<AdminModel> getAllAdmin(){
        return adminService.getAllAdmin();
    }

    @GetMapping("/admin/{adminId}")
    public Mono<AdminModel> getAdminByAdminId(@PathVariable String adminId){
        return adminService.getAdminByAdminId(adminId);
    }

    @PostMapping("/admin/register")
    public Mono<AdminModel> saveAdmin(@RequestBody Mono<AdminModel> adminModelRequest){
        return adminService.saveAdmin(adminModelRequest);
    }

    @PutMapping("/admin/update/{adminId}")
    public Mono<AdminModel> updateAdmin(@RequestBody Mono<AdminModel> adminModelRequest, @PathVariable String adminId)
    {
        return adminService.updateAdmin(adminId,adminModelRequest);
    }

    @DeleteMapping("/admin/delete/{adminId}")
    public Mono<String> deleteAdmin(@PathVariable String adminId){
        return adminService.deleteAdmin(adminId);
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

    @PostMapping("/food/register")
    public Mono<Food> saveFood(@RequestBody Mono<Food> foodMono){
        return foodService.saveFood(foodMono);
    }

    @PutMapping("/food/update/{foodId}")
    public Mono<Food> updateFood(@RequestBody Mono<Food> foodMono, @PathVariable String foodId)
    {
        return foodService.updateFood(foodId,foodMono);
    }

    @DeleteMapping("/food/delete/{foodId}")
    public Mono<String> deleteFood(@PathVariable String foodId){
        return foodService.deleteFood(foodId);
    }

}
