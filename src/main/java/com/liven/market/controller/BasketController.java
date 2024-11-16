package com.liven.market.controller;

import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.service.BasketService;
import com.liven.market.service.dto.request.AddProductToBasketRequestDTO;
import com.liven.market.service.dto.request.CheckoutBasketRequestDTO;
import com.liven.market.service.dto.response.BasketResponseDTO;
import com.liven.market.service.dto.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("create")
    public Response<BasketResponseDTO> createBasket(
    ) {
        Response<BasketResponseDTO> response = new Response<>();
        response.setData(basketService.createUserBasket());
        return response.setOk();
    }


    @GetMapping()
    public Response<List<BasketResponseDTO>> getUserBaskets(
    ) {
        Response<List<BasketResponseDTO>> response = new Response<>();
        response.setData(basketService.getUserBaskets());
        return response.setOk();
    }

    @GetMapping("{id}")
    public Response<BasketResponseDTO> addProductsToBasket(
            @PathVariable("id") UUID basketId
    ) throws ApiErrorException {

        Response<BasketResponseDTO> response = new Response<>();
        response.setData(basketService.getBasketById(basketId));
        return response.setOk();
    }

    @PutMapping("{id}/checkout")
    public Response<BasketResponseDTO> checkoutBasket(
            @PathVariable("id") UUID basketId,
            @Valid @RequestBody CheckoutBasketRequestDTO checkoutBasketRequestDTO
    ) throws ApiErrorException {

        Response<BasketResponseDTO> response = new Response<>();
        response.setData(basketService.checkoutBasket(basketId, checkoutBasketRequestDTO));
        return response.setOk();
    }

    @PutMapping("{id}/add-products")
    public Response<BasketResponseDTO> addProductsToBasket(
            @PathVariable("id") UUID basketId,
            @RequestBody @Valid List<AddProductToBasketRequestDTO> products
    ) throws ApiErrorException {
        Response<BasketResponseDTO> response = new Response<>();
        response.setData(basketService.addProductsToBasket(basketId, products));
        return response.setOk();
    }

}
