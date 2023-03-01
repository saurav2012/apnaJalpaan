package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.admin.AdminModel;
import com.food.apnajalpaan.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    public Mono<AdminModel> saveAdmin(Mono<AdminModel> adminModelMono){
        return adminModelMono.flatMap(adminRepository::insert);
    }
}
