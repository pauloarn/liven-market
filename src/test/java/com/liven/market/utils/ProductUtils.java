package com.liven.market.utils;

import com.liven.market.service.dto.request.CreateProductRequestDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductUtils {
    public List<CreateProductRequestDTO> getProductsListForCreation() {
        List<CreateProductRequestDTO> products = new ArrayList<>();
        List<Integer> auxList = new ArrayList<>(Arrays.asList(1, 2, 3));
        for (Integer i : auxList) {
            CreateProductRequestDTO innerProduct = new CreateProductRequestDTO();
            innerProduct.setAmount(15L);
            innerProduct.setName("Produto " + i);
            innerProduct.setSku("SKUL" + i);
            innerProduct.setPrice(new BigDecimal(2).multiply(new BigDecimal(i)));
            products.add(innerProduct);
        }
        return products;
    }
}
