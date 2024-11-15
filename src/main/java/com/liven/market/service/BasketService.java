package com.liven.market.service;

import com.liven.market.enums.MessageEnum;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.model.Basket;
import com.liven.market.model.BasketProduct;
import com.liven.market.model.IDs.BasketProductId;
import com.liven.market.model.Product;
import com.liven.market.model.User;
import com.liven.market.repository.BasketRepository;
import com.liven.market.service.dto.request.AddProductToBasketRequestDTO;
import com.liven.market.service.dto.response.BasketResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class BasketService extends AbstractServiceRepo<BasketRepository, Basket, UUID> {

    public UserService userService;
    public ProductService productService;

    public BasketService(BasketRepository repository, UserService _userService, ProductService _productService) {
        super(repository);
        this.productService = _productService;
        this.userService = _userService;
    }

    public BasketResponseDTO createUserBasket() {
        log.info("Creating User Basket");
        Basket basket = new Basket();
        User loggedUser = userService.getLoggedUser();
        basket.setUser(loggedUser);
        basket.setTotalValue(new BigDecimal(0));
        repository.save(basket);
        return new BasketResponseDTO(basket);
    }

    public List<BasketResponseDTO> getUserBaskets() {
        List<Basket> userBaskets = this.getUserBasketsByLoggedUser();
        return userBaskets.stream().map(BasketResponseDTO::new).toList();
    }

    private List<Basket> getUserBasketsByLoggedUser() {
        User loggedUser = userService.getLoggedUser();
        return repository.getBasketByUserUserId(loggedUser.getUserId());
    }

    public BasketResponseDTO getBasketById(UUID id) throws ApiErrorException {
        List<Basket> userBaskets = this.getUserBasketsByLoggedUser();
        Basket selectedBasket = userBaskets.stream().filter((basket -> basket.getBasketId().equals(id)))
                .findFirst().orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.BASKET_NOT_FOUND));
        return new BasketResponseDTO(selectedBasket);
    }

    @Transactional
    public BasketResponseDTO addProductsToBasket(UUID basketId, List<AddProductToBasketRequestDTO> products) throws ApiErrorException {
        List<Basket> userBaskets = this.getUserBasketsByLoggedUser();
        Basket selectedBasket = userBaskets.stream().filter((b) -> b.getBasketId().equals(basketId)).findFirst().orElseThrow(
                () -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.BASKET_NOT_FOUND)
        );
        BigDecimal totalBasketValue = new BigDecimal(0);
        for (AddProductToBasketRequestDTO product : products) {
            Product innerProduct = productService.findProduct(product.getProductId());
            addProductToBasket(product, selectedBasket, innerProduct);
            totalBasketValue = totalBasketValue.add(innerProduct.getPrice().multiply(BigDecimal.valueOf(product.getProductAmount())));
        }
        removeNotGivenProductFromBasket(selectedBasket, products);
        selectedBasket.setTotalValue(totalBasketValue);
        repository.save(selectedBasket);
        return new BasketResponseDTO(selectedBasket);
    }

    private void removeNotGivenProductFromBasket(Basket selectedBasket, List<AddProductToBasketRequestDTO> products) {
        List<UUID> givenProducts = products.stream().map(AddProductToBasketRequestDTO::getProductId).toList();
        List<BasketProduct> selectedProducts = selectedBasket.getProductList().stream().filter((product -> givenProducts.contains(product.getProduct().getProductId()))).toList();
        selectedBasket.getProductList().clear();
        selectedBasket.getProductList().addAll(selectedProducts);
    }

    private void addProductToBasket(AddProductToBasketRequestDTO product, Basket selectedBasket, Product loadedProduct)
            throws ApiErrorException {
        if (loadedProduct.getAmount().compareTo(product.getProductAmount()) < 0) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.PRODUCT_ON_STOCK_LESS_THAN_REQUIRED);
        }
        Optional<BasketProduct> selectedProductInBasket = selectedBasket.getProductList().stream().filter(
                (filterProduct) -> filterProduct.getProduct().getProductId().equals(loadedProduct.getProductId())
        ).findFirst();
        if (selectedProductInBasket.isPresent()) {
            selectedProductInBasket.get().setProductAmount(product.getProductAmount());
        } else {
            BasketProductId basketProductId = new BasketProductId(selectedBasket.getBasketId(), loadedProduct.getProductId());
            BasketProduct innerBasketProduct = new BasketProduct();
            innerBasketProduct.setBasketProductId(basketProductId);
            innerBasketProduct.setBasket(selectedBasket);
            innerBasketProduct.setProduct(loadedProduct);
            innerBasketProduct.setProductAmount(product.getProductAmount());
            selectedBasket.getProductList().add(innerBasketProduct);
        }
    }
}
