package com.liven.market.repository;

import com.liven.market.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends GenericRepository<User, UUID> {
    Optional<User> findUsersByEmail(String userEmail);

    long count();
}
