package com.liven.market.repository;

import com.liven.market.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends GenericRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    Optional<Product> findByProductId(UUID productId);
}
