package com.liven.market.repository;

import com.liven.market.model.Basket;

import java.util.List;
import java.util.UUID;

public interface BasketRepository extends GenericRepository<Basket, UUID> {
    List<Basket> getBasketByUserUserId(UUID userId);
}
