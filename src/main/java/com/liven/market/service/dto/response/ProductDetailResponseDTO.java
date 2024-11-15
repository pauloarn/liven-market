package com.liven.market.service.dto.response;

import com.liven.market.model.BasketProduct;
import com.liven.market.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductDetailResponseDTO {
    private UUID id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Long amount;

    public ProductDetailResponseDTO(Product product) {
        this.id = product.getProductId();
        this.sku = product.getSku();
        this.name = product.getName();
        this.price = product.getPrice();
        this.amount = product.getAmount();
    }

    public ProductDetailResponseDTO(BasketProduct product) {
        this.id = product.getProduct().getProductId();
        this.sku = product.getProduct().getSku();
        this.name = product.getProduct().getName();
        this.price = product.getProduct().getPrice();
        this.amount = product.getProductAmount();
    }
}
