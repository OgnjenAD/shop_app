package com.dailycodework.dream_shops.service.cart;

import com.dailycodework.dream_shops.model.CartsItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFormCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartsItem getCartItem(Long cartId, Long productId);
}
