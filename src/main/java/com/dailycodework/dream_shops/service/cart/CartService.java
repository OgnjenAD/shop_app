package com.dailycodework.dream_shops.service.cart;

import com.dailycodework.dream_shops.dto.CartDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Carts;
import com.dailycodework.dream_shops.model.Users;
import com.dailycodework.dream_shops.repository.CartsItemRepository;
import com.dailycodework.dream_shops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartsItemRepository cartsItemRepository;
    private final ModelMapper modelMapper;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Carts getCartById(Long id) {
        Carts carts = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        BigDecimal totalAmount = carts.getTotalAmount();
        carts.setTotalAmount(totalAmount);

        return cartRepository.save(carts);
    }

    @Transactional //mora da se izvrsi odjednom
    @Override
    public void clearCart(Long id) {
        Carts carts = getCartById(id);
        cartsItemRepository.deleteAllByCartsId(id);
        carts.getItems().clear();
        //cartRepository.delete(cart);
        carts.setTotalAmount(BigDecimal.ZERO);
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Carts carts = getCartById(id);
        return carts.getTotalAmount();
        /*return cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

         */
    }

    @Override
    public Carts initializeNewCart(Users user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Carts carts = new Carts();
                    carts.setUsers(user);
                    return cartRepository.save(carts);
                });
    }

    @Override
    public Carts getCartByUserId(Long userId) {
        return cartRepository.findCartsByUsersId(userId);
    }

    @Override
    public CartDto convertToCartDto(Carts carts) {
        return modelMapper.map(carts, CartDto.class);
    }

}
