package com.food.apnajalpaan.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "cart_item")
public class CartItem {
    @Id
    private String cardId;
    @NotEmpty
    private String userId;
    private Food food;
    private Integer items;
    private Double subTotal;
}
