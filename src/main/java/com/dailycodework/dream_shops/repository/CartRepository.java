package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Carts, Long> {
    Carts findCartsByUsersId(Long userId);
}
