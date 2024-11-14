package com.liven.market.model;

import com.liven.market.config.Catalog;
import com.liven.market.config.Schema;
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
@Table(name = "products", catalog = Catalog.MARKET_DATA_BASE, schema = Schema.MarketApp)
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private UUID productId;


    @Column(name = "sku")
    private String sku;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private Double price;

    @Column(name = "amount")
    private Long amount;
}
