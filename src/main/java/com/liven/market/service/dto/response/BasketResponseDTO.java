package com.liven.market.service.dto.response;

import com.liven.market.model.Basket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BasketResponseDTO {

    private UUID basketId;
    private BigDecimal totalValue;
    private List<ProductDetailResponseDTO> products;
    private CheckoutResponseDTO checkout;

    public BasketResponseDTO(Basket basket) {
        this.basketId = basket.getBasketId();
        this.totalValue = basket.getTotalValue();
        this.products = basket.getProductList().stream().map(ProductDetailResponseDTO::new).toList();
        this.checkout = Objects.nonNull(basket.getCheckout()) ? new CheckoutResponseDTO(basket.getCheckout()) : null;
    }
}
