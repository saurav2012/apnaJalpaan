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
    private String username;          // username of user
    private String reservationType;
    private String date;
    private Integer numOfGuest;
    private String time;
    private Boolean isConfirmed;
}
