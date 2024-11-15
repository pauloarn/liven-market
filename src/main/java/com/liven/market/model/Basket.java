package com.liven.market.model;

import com.liven.market.config.Catalog;
import com.liven.market.config.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "baskets", catalog = Catalog.MARKET_DATA_BASE, schema = Schema.MarketApp)
public class Basket extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private UUID basketId;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @ManyToOne()
    @JoinColumn(name = "user_fk")
    private User user;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BasketProduct> productList = new ArrayList<>();
}
