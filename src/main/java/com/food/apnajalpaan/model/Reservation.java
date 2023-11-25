package com.food.apnajalpaan.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reservation")
public class Reservation {
    @Id
    private String reservationId;
    @NotEmpty(message = "Login to reserve table")
    @NotNull(message = "Login to reserve table")
    private String username;          // username of user
    @NotEmpty(message = "Reservation type must be present")
    private String reservationType;
    @NotEmpty(message = "Choose a date")
    private String date;
    @Max(value = 100,message = "Enter number of guest upto 100")
    private Integer numOfGuest;
    @NotEmpty(message = "Select time")
    private String time;
    private Boolean isConfirmed = false;
    private Boolean isExpired = false;
    private String specialMessage;
}
