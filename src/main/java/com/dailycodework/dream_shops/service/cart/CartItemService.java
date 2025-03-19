package com.dailycodework.dream_shops.service.cart;

import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Carts;
import com.dailycodework.dream_shops.model.CartsItem;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CartsItemRepository;
import com.dailycodework.dream_shops.repository.CartRepository;
import com.dailycodework.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartsItemRepository cartsItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Carts carts = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);
        CartsItem cartsItem = carts.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(new CartsItem());
        if (cartsItem.getId() == null) {
            cartsItem.setCarts(carts);
            cartsItem.setProduct(product);
            cartsItem.setQuantity(quantity);
            cartsItem.setUnitePrice(product.getPrice());
        } else {
            cartsItem.setQuantity(cartsItem.getQuantity() + quantity);
        }
        cartsItem.setTotalPrice();
        carts.addItem(cartsItem);
        cartsItemRepository.save(cartsItem);
        cartRepository.save(carts);
    }

    @Override
    public void removeItemFormCart(Long cartId, Long productId) {
        Carts carts = cartService.getCartById(cartId);
        CartsItem itemToRemove = getCartItem(cartId, productId);
        carts.removeItem(itemToRemove);
        cartRepository.save(carts);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Carts carts = cartService.getCartById(cartId);
        carts.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitePrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = carts.getItems()
                .stream().map(CartsItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carts.setTotalAmount(totalAmount);
        cartRepository.save(carts);
    }

    @Override
    public CartsItem getCartItem(Long cartId, Long productId) {
        Carts carts = cartService.getCartById(cartId);
        return carts.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found!"));
    }
}
