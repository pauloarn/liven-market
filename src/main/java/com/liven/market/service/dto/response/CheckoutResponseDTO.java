package com.liven.market.service.dto.response;

import com.liven.market.enums.PaymentEnum;
import com.liven.market.model.Checkout;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CheckoutResponseDTO {
    private PaymentEnum paymentMethod;
    private LocalDateTime checkoutDate;

    public CheckoutResponseDTO(Checkout checkout) {
        this.paymentMethod = checkout.getPaymentMethod();
        this.checkoutDate = checkout.getCreatedAt();
    }
}
