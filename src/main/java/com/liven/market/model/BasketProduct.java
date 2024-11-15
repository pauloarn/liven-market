package com.liven.market.model;

import com.liven.market.config.Catalog;
import com.liven.market.config.Schema;
import com.liven.market.model.IDs.BasketProductId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "basket_product", catalog = Catalog.MARKET_DATA_BASE, schema = Schema.MarketApp)
public class BasketProduct {
    @EmbeddedId
    private BasketProductId basketProductId;

    @MapsId("basketId")
    @JoinColumn(name = "basket_fk")
    @ManyToOne
    private Basket basket;

    @MapsId("productId")
    @JoinColumn(name = "product_fk")
    @ManyToOne
    private Product product;

    @Column(name = "product_amount")
    private Long productAmount;

}
