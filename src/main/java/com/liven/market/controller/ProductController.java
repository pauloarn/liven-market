package com.liven.market.controller;

import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.service.ProductService;
import com.liven.market.service.dto.request.CreateProductRequestDTO;
import com.liven.market.service.dto.request.ProductPaginateRequestDTO;
import com.liven.market.service.dto.request.UpdateProductRequestDTO;
import com.liven.market.service.dto.response.ProductDetailResponseDTO;
import com.liven.market.service.dto.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("create")
    public Response<ProductDetailResponseDTO> createProduct(
            @RequestBody @Valid CreateProductRequestDTO createUserRequestDTO
    ) throws ApiErrorException {
        Response<ProductDetailResponseDTO> response = new Response<>();
        response.setData(productService.createProduct(createUserRequestDTO));
        return response.setOk();
    }


    @PutMapping("update/{id}")
    public Response<ProductDetailResponseDTO> updateProduct(
            @PathVariable("id") UUID id,
            @RequestBody @Valid UpdateProductRequestDTO createUserRequestDTO
    ) throws ApiErrorException {
        Response<ProductDetailResponseDTO> response = new Response<>();
        response.setData(productService.updateProduct(id, createUserRequestDTO));
        return response.setOk();
    }

    @GetMapping("{id}")
    public Response<ProductDetailResponseDTO> getProduct(
            @PathVariable("id") UUID id
    ) throws ApiErrorException {
        Response<ProductDetailResponseDTO> response = new Response<>();
        response.setData(productService.getProduct(id));
        return response.setOk();
    }

    @GetMapping("/list")
    public Response<Page<ProductDetailResponseDTO>> getProductsList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lowerPrice", required = false) Double lowerPrice,
            @RequestParam(value = "higherPrice", required = false) Double higherPrice
    ) throws ApiErrorException {
        Response<Page<ProductDetailResponseDTO>> response = new Response<>();
        ProductPaginateRequestDTO paginationData = new ProductPaginateRequestDTO(page, size, sku, name, lowerPrice, higherPrice);
        response.setData(productService.getProductList(paginationData));
        return response.setOk();
    }

}