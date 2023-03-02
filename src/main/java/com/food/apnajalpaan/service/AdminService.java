package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.admin.AdminModel;
import com.food.apnajalpaan.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private AdminRepository repository;
    public Mono<AdminModel> saveAdmin(Mono<AdminModel> adminModelMono){
        return adminModelMono.flatMap(repository::insert);
    }

    public Mono<AdminModel> updateAdmin(String adminId,Mono<AdminModel> adminModelMono){
        return repository.findById(adminId)
                .flatMap(res -> {
                    return adminModelMono.flatMap(
                            x -> {
                                if(x.getUsername()!=null) res.setUsername(x.getUsername());
                                if(x.getAge()!=null) res.setAge(x.getAge());
                                if(x.getEmail()!=null) res.setEmail(x.getEmail());
                                if(x.getGender()!=null) res.setGender(x.getGender());
                                if(x.getMobile()!=null) res.setMobile(x.getMobile());
                                if(x.getPassword()!=null) res.setPassword(x.getPassword());
                                if(x.getFirstName()!=null) res.setFirstName(x.getFirstName());
                                if(x.getLastName()!=null) res.setLastName(x.getLastName());
                                if(x.getAddress()!=null) res.setAddress(x.getAddress());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<String> deleteAdmin(String adminId){
        repository.deleteById(adminId);
        return Mono.just("Admin is deleted successfully");
    }

    public Flux<AdminModel> getAllAdmin() {
        return repository.findAll();
    }
    public Mono<AdminModel> getAdminByAdminId(String adminId) {
        return repository.findById(adminId);
    }

}
