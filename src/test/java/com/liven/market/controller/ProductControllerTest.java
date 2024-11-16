package com.liven.market.controller;


import com.liven.market.LivenMarketApplication;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.service.dto.request.CreateProductRequestDTO;
import com.liven.market.service.dto.response.ProductDetailResponseDTO;
import com.liven.market.service.dto.response.Response;
import com.liven.market.utils.ProductUtils;
import com.liven.market.utils.UsuarioMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LivenMarketApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    ProductController productController;

    private ProductUtils productUtils;

    private String baseUserEmail = "teste@test.com";
    private String baseUserName = "User Test";

    @BeforeAll
    public void beforeAll() {
        this.productUtils = new ProductUtils();
    }

    @BeforeEach
    public void beforeEach() {
        UsuarioMock.builder()
                .setEmail(baseUserEmail)
                .setUserName(baseUserName)
                .build();
    }

    @Test
    @Order(1)
    public void createProductTest() throws ApiErrorException {
        List<CreateProductRequestDTO> productToCreate = productUtils.getProductsListForCreation();
        for (CreateProductRequestDTO product : productToCreate) {
            productController.createProduct(product);
        }
        Response<Page<ProductDetailResponseDTO>> paginatedProducts = productController.getProductsList(
                1,
                15,
                null,
                null,
                null,
                null
        );
        Assertions.assertEquals(productToCreate.size(), paginatedProducts.getBody().getTotalElements());
    }


}
