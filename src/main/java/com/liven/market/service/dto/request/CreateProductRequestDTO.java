package com.liven.market.service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDTO {

    @NotNull
    @Size(min = 3, max = 255)
    private String sku;

    @NotNull
    @Size(min = 6, max = 255)
    private String name;

    @NotNull
    private Double price;

    @NotNull
    @Min(0)
    private Long amount;
}