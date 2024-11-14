package com.liven.market.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductPaginateRequestDTO {
    private Integer page;
    private Integer pageSize;
    private String sku;
    private String name;
    private Double lowerPrice;
    private Double higherPrice;
}
