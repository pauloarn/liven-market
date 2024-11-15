package com.liven.market.service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequestDTO {
    @NotNull
    @Size(min = 6, max = 255)
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Long amount;
}
