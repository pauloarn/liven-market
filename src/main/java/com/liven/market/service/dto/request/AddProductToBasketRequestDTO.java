package com.liven.market.service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddProductToBasketRequestDTO {
    @NotNull
    private UUID productId;
    
    @NotNull
    @Min(1)
    private Long productAmount;
}
