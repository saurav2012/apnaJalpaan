package com.food.apnajalpaan.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "cart_item")
public class CartItem {
    @Id
    private String cartId;
    @NotEmpty
    private String userId;
    private Food food;
    private Integer items;
    private Double subTotal;
}
