package com.liven.market.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddProductToBasketRequestDTO {
    private UUID productId;
    private Long productAmount;
}
