package com.food.apnajalpaan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reservation")
public class Reservation {
    @Id
    private String reservationId;
    private String userId;          // id of user
    private String reservationType;
    private LocalDateTime date;
    private Integer numOfGuest;
    private LocalTime time;
    private Boolean isConfirmed;
}
