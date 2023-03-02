package com.food.apnajalpaan.controllers;

import com.food.apnajalpaan.model.admin.AdminModel;
import com.food.apnajalpaan.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin")
public class ApnaJalPaanController {
    @Autowired
    AdminService adminService;

    @GetMapping()
    public Flux<AdminModel> getAllAdmin(){
        return adminService.getAllAdmin();
    }

    @GetMapping("/{adminId}")
    public Mono<AdminModel> getAdminByAdminId(@PathVariable String adminId){
        return adminService.getAdminByAdminId(adminId);
    }

    @PostMapping("/register")
    public Mono<AdminModel> saveAdmin(@RequestBody Mono<AdminModel> adminModelRequest){
        return adminService.saveAdmin(adminModelRequest);
    }

    @PutMapping("/update/{adminId}")
    public Mono<AdminModel> updateAdmin(@RequestBody Mono<AdminModel> adminModelRequest, @PathVariable String adminId)
    {
        return adminService.updateAdmin(adminId,adminModelRequest);
    }

    @DeleteMapping("/delete/{adminId}")
    public Mono<String> deleteAdmin(@PathVariable String adminId){
        return adminService.deleteAdmin(adminId);
    }

}
