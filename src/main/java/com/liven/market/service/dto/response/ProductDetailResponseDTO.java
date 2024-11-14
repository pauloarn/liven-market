package com.liven.market.service.dto.response;

import com.liven.market.model.Product;
import com.liven.market.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDetailResponseDTO {
    private UUID id;
    private String sku;
    private String name;
    private Double price;
    private Long amount;

    public ProductDetailResponseDTO(Product product){
        this.id = product.getProductId();
        this.sku = product.getSku();
        this.name = product.getName();
        this.price = product.getPrice();
        this.amount = product.getAmount();
    }
}
