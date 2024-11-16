package com.liven.market.service.dto.request;

import com.liven.market.enums.PaymentEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutBasketRequestDTO {
    @NotNull
    private PaymentEnum paymentMethod;
}
