package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.OrderDto;
import com.dailycodework.dream_shops.enums.OrderStatus;
import com.dailycodework.dream_shops.model.*;
import com.dailycodework.dream_shops.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = OrderController.class, useDefaultFilters = false)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    /*@MockBean
    private JwtUtils jwtUtils;*/

    private Users user;
    private Carts cart;
    private Set<CartsItem> cartsItems;
    private Order order;
    private Set<Role> userRole;
    private Product product;
    private Category category;
    private OrderItem orderItem;
    private OrderDto orderDto;

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

        orderDto = orderService.convertToDto(order);
    }

    @Test
    void OrderController_createOrder() throws Exception {
        given(orderService.placeOrder(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/orders/order")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1")
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount", CoreMatchers.is(order.getTotalAmount())));
    }

    @Test
    void OrderController_getOrderById() throws Exception {
        when(orderService.getOrder(1L)).thenReturn(orderDto);

        ResultActions response = mockMvc.perform(get("/order")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
