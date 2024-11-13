package com.liven.market.repository;

import com.liven.market.model.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Long> {
    Optional<User> findUsersByEmail(String userEmail);
    long count();
}
