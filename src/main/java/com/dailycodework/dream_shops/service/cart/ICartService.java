package com.dailycodework.dream_shops.service.cart;

import com.dailycodework.dream_shops.dto.CartDto;
import com.dailycodework.dream_shops.model.Carts;
import com.dailycodework.dream_shops.model.Users;

import java.math.BigDecimal;

public interface ICartService {

    Carts getCartById(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Carts initializeNewCart(Users user);

    Carts getCartByUserId(Long userId);

    CartDto convertToCartDto(Carts carts);
}
