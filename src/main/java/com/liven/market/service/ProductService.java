package com.liven.market.service;

import com.liven.market.enums.MessageEnum;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.model.Product;
import com.liven.market.repository.ProductRepository;
import com.liven.market.service.dto.request.CreateProductRequestDTO;
import com.liven.market.service.dto.request.ProductPaginateRequestDTO;
import com.liven.market.service.dto.request.UpdateProductRequestDTO;
import com.liven.market.service.dto.response.ProductDetailResponseDTO;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Log4j2
public class ProductService extends AbstractServiceRepo<ProductRepository, Product, UUID> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }

    public ProductDetailResponseDTO createProduct(CreateProductRequestDTO productRequest) throws ApiErrorException {
        validateProductRequest(productRequest);
        log.info("Creating Product");
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setSku(productRequest.getSku());
        product.setAmount(productRequest.getAmount());
        product.setPrice(productRequest.getPrice());
        repository.save(product);
        log.info("Product created");
        return new ProductDetailResponseDTO(product);
    }

    public ProductDetailResponseDTO updateProduct(UUID productId, UpdateProductRequestDTO productRequest) throws ApiErrorException {
        log.info("Updating Product");
        Product product = validateUpdateProductRequest(productId, productRequest);
        product.setName(productRequest.getName());
        product.setAmount(productRequest.getAmount());
        product.setPrice(productRequest.getPrice());
        repository.save(product);
        log.info("Product Updated");
        return new ProductDetailResponseDTO(product);
    }

    private Product validateUpdateProductRequest(UUID productId, UpdateProductRequestDTO productRequest) throws ApiErrorException {
        log.info("Validating Product");
        Optional<Product> product = repository.findByProductId(productId);
        if (product.isEmpty()) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.PRODUCT_NOT_REGISTERED);
        }
        if (productRequest.getAmount() < 0) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.AMOUNT_MUST_NOT_BE_NEGATIVE);
        }
        if (productRequest.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.PRICE_MUST_BE_GREATER_THAN_0);
        }
        log.info("Product Validated");
        return product.get();
    }

    private void validateProductRequest(CreateProductRequestDTO product) throws ApiErrorException {
        log.info("Validating Product");
        Optional<Product> skuProduct = repository.findBySku(product.getSku());
        if (skuProduct.isPresent()) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.SKU_ALREADY_REGISTERED);
        }
        if (product.getAmount() < 0) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.AMOUNT_MUST_NOT_BE_NEGATIVE);
        }
        if (product.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.PRICE_MUST_BE_GREATER_THAN_0);
        }

        log.info("Product Validated");
    }

    public ProductDetailResponseDTO getProduct(UUID productId) throws ApiErrorException {
        Optional<Product> product = repository.findByProductId(productId);
        if (product.isEmpty()) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.PRODUCT_NOT_REGISTERED);
        }
        return new ProductDetailResponseDTO(product.get());
    }

    public Product findProduct(UUID productId) throws ApiErrorException {
        return repository.findById(productId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.PRODUCT_NOT_REGISTERED));
    }

    public Page<ProductDetailResponseDTO> getProductList(ProductPaginateRequestDTO paginationData) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        List<Predicate> andPredicates = new ArrayList<>();
        Root<Product> from = criteriaQuery.from(Product.class);
        List<Order> orderList = new ArrayList<>();

        andPredicates.add(criteriaBuilder.greaterThan(from.get("amount"), 0));

        if (Objects.nonNull(paginationData.getName())) {
            andPredicates.add(criteriaBuilder.like(from.get("name"), "%" + paginationData.getName() + "%"));
        }
        if (Objects.nonNull(paginationData.getSku())) {
            andPredicates.add(criteriaBuilder.like(from.get("sku"), "%" + paginationData.getName() + "%"));
        }

        if (Objects.nonNull(paginationData.getLowerPrice())) {
            andPredicates.add(criteriaBuilder.greaterThan(from.get("price"), paginationData.getLowerPrice()));
        }

        if (Objects.nonNull(paginationData.getHigherPrice())) {
            andPredicates.add(criteriaBuilder.lessThan(from.get("price"), paginationData.getHigherPrice()));
        }

        criteriaQuery = criteriaQuery.select(from).where(andPredicates.toArray(new Predicate[andPredicates.size()]));
        orderList.add(criteriaBuilder.asc(from.get("name")));
        criteriaQuery.orderBy(orderList.toArray(new Order[0]));
        TypedQuery<Product> query = em.createQuery(criteriaQuery);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(criteriaQuery.getResultType());
        countRoot.alias(from.getAlias());
        countQuery.select(criteriaBuilder.count(countRoot));
        Long totalExercises = em.createQuery(countQuery).getSingleResult();
        log.info("Total de Exercicios encontrados: {}", totalExercises);

        int page = paginationData.getPage();
        int sizePage = paginationData.getPageSize();
        Pageable pageable = generatePageable(page, sizePage);
        page = pageable.getPageNumber();
        query.setFirstResult(page * sizePage);
        query.setMaxResults(sizePage);
        log.info("Buscando os exercicios");
        List<Product> productsList = query.getResultList();
        List<ProductDetailResponseDTO> exercisesDTO = new ArrayList<>();
        for (Product product : productsList) {
            exercisesDTO.add(new ProductDetailResponseDTO(product));
        }
        return new PageImpl<>(exercisesDTO, pageable, totalExercises);
    }
}
