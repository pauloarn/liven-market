package com.liven.market.model.IDs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BasketProductId {

    @Column(name = "basket_fk")
    private UUID basketId;

    @Column(name = "product_fk")
    private UUID productId;
}
