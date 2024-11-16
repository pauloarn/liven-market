package com.liven.market.model;


import com.liven.market.config.Catalog;
import com.liven.market.config.Schema;
import com.liven.market.enums.PaymentEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "checkouts", catalog = Catalog.MARKET_DATA_BASE, schema = Schema.MarketApp)
public class Checkout extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private UUID checkoutId;


    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentEnum paymentMethod;

    @OneToOne()
    @JoinColumn(name = "basket_fk")
    private Basket basket;
}
