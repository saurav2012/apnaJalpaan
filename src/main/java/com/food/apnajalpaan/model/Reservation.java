package com.food.apnajalpaan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Choose a date")
    private String date;
    @NotEmpty(message = "Enter number of guest")
    private Integer numOfGuest;
    @NotEmpty(message = "Select time")
    private String time;
    private Boolean isConfirmed = false;
}
