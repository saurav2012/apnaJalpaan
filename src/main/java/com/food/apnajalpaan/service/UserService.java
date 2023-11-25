package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Order;
import com.food.apnajalpaan.model.Role;
import com.food.apnajalpaan.model.Status;
import com.food.apnajalpaan.model.user.UserModel;
import com.food.apnajalpaan.repository.UserRepository;
import com.food.apnajalpaan.utility.EmailHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private EmailHelper emailHelper;
    @Autowired
    private OrderService orderService;

    static final String EMAIL_BODY_USER_WELCOME = "Greetings,#FIRSTNAME#\n" +
            " Welcome to ApnaJalPaan family!!\n" +
            "Thankyou for choosing us, we are happy to serve you.\n" +
            "Some vouchers have been added to your account, enjoy delicious and healthy food with us!\n" +
            "For more details visit: #WEBSITELINK# \n \n" +
            "Regards\n" +
            "ApnaJalPaan Restaurant: #ADDRESS#";
    static final String websiteLink = "http://www.apnajalpaan.com";
    static final String FIRST_NAME = "#FIRSTNAME#";
    static final String ADDRESS = "#ADDRESS#";
    public final String locationlink = "https://www.google.com/maps/place/Barbeque+Nation/@28.6294697,77.0695303,16z/data=!3m1!5s0x390d04c118c5eb15:0xe80248a5f2cad368!4m10!1m2!2m1!1sbbq+janakpuri!3m6!1s0x390d04afaaaaaaab:0x9f4338b72d5b02c8!8m2!3d28.6288372!4d77.0766715!15sCg1iYnEgamFuYWtwdXJpWg8iDWJicSBqYW5ha3B1cmmSARFidWZmZXRfcmVzdGF1cmFudOABAA!16s%2Fg%2F1tdqy2z2";
    static final String WEBSITE_LINK = "#WEBSITELINK#";
    static final Integer daysOfExp = 60;
    @Lazy
    @Autowired
    CouponService couponService;
    @Autowired
    PasswordEncoder passwordEncoder;
    public Mono<Object> saveUser(Mono<UserModel> userModelMono){
        return userModelMono
                .flatMap(userModel ->
                        repository.findByUsername(userModel.getUsername())
                                .flatMap(existingUser -> {
                                    // User with the same username or email already exists, handle accordingly
                                    return Mono.error(new RuntimeException("User with the same username or email already exists"));
                                })
                                .switchIfEmpty(Mono.defer(() -> {
                                    // No existing user found, proceed with saving the new user
                                    userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
                                    userModel.setDoj(LocalDate.now().toString());
                                    userModel.setIsActive(true);
                                    userModel.setRole(Role.ROLE_USER);
                                    return repository.insert(userModel);
                                }))
                );
    }

    // with mail as notification...
    public Mono<UserModel> saveUserWithNotification(Mono<UserModel> userModelMono){
        return userModelMono.flatMap(
                res -> {
                    res.setPassword(passwordEncoder.encode(res.getPassword()));
                    res.setDoj(LocalDate.now().toString());
                    return Mono.just(res);
                }
        ).flatMap(repository::insert).flatMap(user -> {
            String body = EMAIL_BODY_USER_WELCOME.replace(FIRST_NAME,user.getFirstName())
                    .replace(WEBSITE_LINK,websiteLink)
                    .replace(ADDRESS,locationlink);
            emailHelper.sendSimpleEmail(user.getEmail(), body,"Welcome to ApnaJalPaan Family!");
            return Mono.just(user);
        });
    }

    public Mono<UserModel> updateUser(String userId, Mono<UserModel> userModelMono){
        return repository.findById(userId)
                .flatMap(res -> {
                    return userModelMono.flatMap(
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
                                if (x.getCouponIds()!=null) res.setCouponIds(x.getCouponIds());
                                if(x.getRole()!=null) res.setRole(x.getRole());
                                if(x.getIsActive()!=null) res.setIsActive(x.getIsActive());
                                if(x.getBirthdate()!=null) res.setBirthdate(x.getBirthdate());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<String> deleteUser(String userId){
        repository.deleteById(userId);
        return Mono.just("user is deleted successfully");
    }

    public Flux<UserModel> getAllUser() {
        return repository.findAll();
    }
    public Mono<UserModel> getUserByUserId(String userId) {
        return repository.findById(userId);
    }

    public Mono<UserModel> getUserByUsername(String username){
        return repository.findByUsername(username);
    }

    @Scheduled(cron = "${cron.expression.one-day}", zone = "${cron.timezone}")
    public Flux<Object> assignCouponIdsForFirstTime(){
        List<String> couponCodeList = List.of("FLAT50","FLAT20","UPTO20");
        repository.findAll().flatMap(
            user -> {
                if(user.getDoj().equalsIgnoreCase(LocalDate.now().toString())) {
                    couponCodeList.forEach(
                        code -> {
                            couponService.findByCode(code).flatMap(
                                coupon -> {
                                    if(!user.getCouponIds().containsKey(coupon.getCouponId())){
                                        user.getCouponIds().put(coupon.getCouponId(),new Status(false,false,LocalDate.now().plusDays(daysOfExp).toString()));
                                        System.out.println("after update");
                                    }
                                    System.out.println("in coupon");
                                    return Mono.just(coupon);
                                }
                            ).subscribe();
                        }
                    );
                }
                // set coupon is expired here!!
                user.getCouponIds().values().forEach(id -> {
                    id.setExpDate(LocalDate.now().plusDays(daysOfExp).toString());
                });
                return Mono.just(user);
            }
        ).flatMap(repository::save).subscribe();
        return Flux.empty();
    }
    // everymonth for new or existing user remaining coupon....
    @Scheduled(cron = "${cron.expression.one-month}", zone = "${cron.timezone}")
    public Flux<Object> assignCouponIdsForEveryMonthTime() {
        List<String> couponCodeList = List.of("FLAT10", "UPTO30", "BUY1000GET100");
        repository.findAll().flatMap(
                user -> {
                    if (user.getDoj().equalsIgnoreCase(LocalDate.now().minusDays(30).toString())) {
                        couponCodeList.forEach(
                                code -> {
                                    couponService.findByCode(code).flatMap(
                                            coupon -> {
                                                if (!user.getCouponIds().containsKey(coupon.getCouponId())) {
                                                    user.getCouponIds().put(coupon.getCouponId(), new Status(false, false, LocalDate.now().plusDays(60).toString()));
                                                    System.out.println("after update months");
                                                }
                                                System.out.println("in coupon months");
                                                return Mono.just(coupon);
                                            }
                                    ).subscribe();
                                }
                        );
                    }
                    // set coupon is expired here!!
                    user.getCouponIds().values().forEach(id -> {
                        id.setExpDate(LocalDate.now().plusDays(daysOfExp).toString());
                    });
                    return Mono.just(user);
                }
        ).flatMap(repository::save).subscribe();
        return Flux.empty();
    }
}
