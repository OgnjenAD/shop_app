package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.CartsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsItemRepository extends JpaRepository<CartsItem, Long> {
    void deleteAllByCartsId(Long id);
}
