package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);

    Users findUsersByEmail(String email);
}
