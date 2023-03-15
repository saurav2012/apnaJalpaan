package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Reservation;
import com.food.apnajalpaan.repository.ReservationRepository;
import com.food.apnajalpaan.utility.EmailHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    ReservationRepository repository;

    @Autowired
    EmailHelper emailHelper;

    static final String USERNAME_STR = "#USERNAME#";
    static final String APPROVE_STR = "#APPROVE#";
    static final String DENIED_STR = "#DENIED#";
    static final String RESERVATION_DATE = "#RES_DATE#";

    static final String FIRST_NAME = "#FIRSTNAME#";
    static final String RESERVATION_TYPE = "#RES_TYPE#";

    static final String LOCATION_LINK = "#LOCATION#";

    static final String EMAIL_BODY_ADMIN = "Hi we have a reservation request with username #USERNAME#.\n" +
            "Please visit #APPROVE# to approve\nor you can visit #DENIED# to deny the request.";
    static final String EMAIL_BODY_USER_APPROVE = "Greetings\n" +
            "#FIRSTNAME# We are pleased to inform you that your reservation\n" +
            "for #RES_TYPE# on #RES_DATE# at ApnaJalPaan Resturant is approved!\n \n \n Location: #LOCATION#";
    static final String EMAIL_BODY_USER_DENY = "Hello #FIRSTNAME#\n" +
            "We are sorry to inform you that your reservation\n" +
            "for #RES_TYPE# on #RES_DATE# at ApnaJalPaan Resturant can not fulfilled. Please choose some other time or date!\n Thankyou for choosing us!!";
    static final String ADMIN_EMAIL = "20121998saurav@gmail.com";

    public final String apporveLink = "https://stackoverflow.com/questions/43509987/how-do-i-set-default-values-for-instance-variables";
    public final String deniedLink = "https://stackoverflow.com/questions/43509987/how-do-i-set-default-values-for-instance-variables";
    public final String locationlink = "https://www.google.com/maps/place/Barbeque+Nation/@28.6294697,77.0695303,16z/data=!3m1!5s0x390d04c118c5eb15:0xe80248a5f2cad368!4m10!1m2!2m1!1sbbq+janakpuri!3m6!1s0x390d04afaaaaaaab:0x9f4338b72d5b02c8!8m2!3d28.6288372!4d77.0766715!15sCg1iYnEgamFuYWtwdXJpWg8iDWJicSBqYW5ha3B1cmmSARFidWZmZXRfcmVzdGF1cmFudOABAA!16s%2Fg%2F1tdqy2z2";

    @Autowired
    UserService userService;
    public Mono<Reservation> saveReservation(Mono<Reservation> reservationMono){
        // exception handling left...
        return reservationMono.flatMap(
                reservation -> {
                    return userService.getUserByUsername(reservation.getUsername()).flatMap(
                            user -> {
                                String body = EMAIL_BODY_ADMIN.replace(USERNAME_STR, user.getUsername()).replace(APPROVE_STR,apporveLink).replace(DENIED_STR,deniedLink);
                                emailHelper.sendSimpleEmail(ADMIN_EMAIL,body,"New Reservation from "+user.getFirstName());
                                return Mono.just(reservation);
                            }
                    );
                }
        ).flatMap(repository::insert);
    }

    public Mono<Reservation> updateReservation(String reservationId,Mono<Reservation> reservationMono){
        return repository.findById(reservationId)
                .flatMap(res -> {
                    return reservationMono.flatMap(
                            x -> {
                                if(x.getReservationType()!=null) res.setReservationType(x.getReservationType());
                                if(x.getIsConfirmed()!=null) res.setIsConfirmed(x.getIsConfirmed());
                                if(x.getNumOfGuest()!=null) res.setNumOfGuest(x.getNumOfGuest());
                                if(x.getReservationType()!=null) res.setReservationType(x.getReservationType());
                                if(x.getTime()!=null) res.setTime(x.getTime());
                                return Mono.just(res);
                            });
                })
                .flatMap(repository::save);
    }

    public Mono<Void> deleteReservation(String reservationId){
        return repository.deleteById(reservationId);
    }

    public Flux<Reservation> getAllReservation() {
        return repository.findAll();
    }
    public Mono<Reservation> getReservationByReservationId(String reservationId) {
        return repository.findById(reservationId);
    }

    public Mono<Reservation> approveReservationByAdmin(String reservationId,Mono<Reservation> reservationMono){
        return updateReservation(reservationId,reservationMono).flatMap(
                reservation -> {
                    return userService.getUserByUsername(reservation.getUsername()).flatMap(
                            user -> {
                                String body = EMAIL_BODY_USER_APPROVE
                                        .replace(FIRST_NAME, user.getFirstName())
                                        .replace(RESERVATION_DATE,reservation.getDate())
                                        .replace(RESERVATION_TYPE,reservation.getReservationType())
                                        .replace(LOCATION_LINK,locationlink);
                                emailHelper.sendSimpleEmail(user.getEmail(), body,"Reservation at ApnaJalPaan Restaurant");
                                return Mono.just(reservation);
                            }
                    );
                }
        );
    }
    public Mono<Reservation> denyReservationByAdmin(String reservationId,Mono<Reservation> reservationMono){
        return updateReservation(reservationId,reservationMono).flatMap(
                reservation -> {
                    return userService.getUserByUsername(reservation.getUsername()).flatMap(
                            user -> {
                                String body = EMAIL_BODY_USER_DENY
                                        .replace(FIRST_NAME, user.getFirstName())
                                        .replace(RESERVATION_DATE,reservation.getDate())
                                        .replace(RESERVATION_TYPE,reservation.getReservationType());
                                emailHelper.sendSimpleEmail(user.getEmail(),body,"Reservation at ApnaJalPaan Restaurant");
                                return Mono.just(reservation);
                            }
                    );
                }
        );
    }

}
