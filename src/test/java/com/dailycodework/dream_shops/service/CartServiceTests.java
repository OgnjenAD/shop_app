package com.dailycodework.dream_shops.service;

import com.dailycodework.dream_shops.enums.OrderStatus;
import com.dailycodework.dream_shops.model.*;
import com.dailycodework.dream_shops.repository.CartRepository;
import com.dailycodework.dream_shops.repository.CartsItemRepository;
import com.dailycodework.dream_shops.service.cart.CartService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;


//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartsItemRepository cartsItemRepository;

    private Users user;
    private Carts cart;
    private Set<CartsItem> cartsItems;
    private Order order;
    private Set<Role> userRole;
    private Product product;
    private Category category;
    private OrderItem orderItem;

    @BeforeEach
    public void init() {
        category = new Category("Smartwatch");
        product = new Product("Samsung 1 Pro Watch", "Samsung", new BigDecimal(100), 10, "Samsung smart watch", category);

        cartsItems = new HashSet<>( Set.of(new CartsItem(1L, 2, new BigDecimal(100), new BigDecimal(200), product, cart)));
        cart = new Carts(1L, new BigDecimal(200), cartsItems, user);

        orderItem = new OrderItem(order, product, 1, new BigDecimal(100));
        order = new Order(1L, LocalDate.now(), new BigDecimal(2), OrderStatus.PENDING, Set.of(orderItem), user);

        userRole = Set.of(new Role("USER_ROLE"));
        user = new Users(1L, "User", "Lastname",
                "user@email.com", "123456",
                cart, List.of(order),
                userRole);
    }

    @Test
    public void CartService_getCartById() {
        when(cartRepository.findCartsByUsersId(user.getId())).thenReturn(cart);

        Carts carts = cartService.getCartByUserId(user.getId());

        Assertions.assertThat(carts).isNotNull();
        Assertions.assertThat(user.getCarts()).isEqualTo(carts);
    }

    @Test
    public void CartService_clearCart() {
        when(cartRepository.save(Mockito.any(Carts.class))).thenReturn(cart);
        when(cartRepository.findById(user.getId())).thenReturn(Optional.ofNullable(cart));

        cartService.clearCart(cart.getId());

        Assertions.assertThat(cart.getItems()).isEmpty();
    }

    @Test
    public void CartService_getTotalPrice() {
        when(cartRepository.save(Mockito.any(Carts.class))).thenReturn(cart);
        when(cartRepository.findById(user.getId())).thenReturn(Optional.ofNullable(cart));

        BigDecimal totalPrice = cartService.getTotalPrice(cart.getId());

        Assertions.assertThat(totalPrice).isEqualTo(BigDecimal.valueOf(200));
    }
}
