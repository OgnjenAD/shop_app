package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.enums.OrderStatus;
import com.dailycodework.dream_shops.model.*;
import com.dailycodework.dream_shops.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ProductController.class, useDefaultFilters = false)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Users user;
    private Carts cart;
    private Set<CartsItem> cartsItems;
    private Order order;
    private Set<Role> userRole;
    private Product product;
    private Category category;
    private OrderItem orderItem;
    private ProductDto productDto;


    @BeforeEach
    public void init() {
        category = new Category("Smartwatch");
        product = new Product("Samsung 1 Pro Watch", "Samsung", new BigDecimal(100), 10, "Samsung smart watch", category);

        cartsItems = new HashSet<>(Set.of(new CartsItem(1L, 2, new BigDecimal(100), new BigDecimal(200), product, cart)));
        cart = new Carts(1L, new BigDecimal(200), cartsItems, user);

        orderItem = new OrderItem(order, product, 1, new BigDecimal(100));
        order = new Order(1L, LocalDate.now(), new BigDecimal(2), OrderStatus.PENDING, Set.of(orderItem), user);

        userRole = Set.of(new Role("USER_ROLE"));
        user = new Users(1L, "User", "Lastname",
                "user@email.com", "123456",
                cart, List.of(order),
                userRole);

        productDto = productService.convertToDto(product);
    }

    @Test
    void ProductController_getProductByBrandAndName() throws Exception {
        when(productService.getProductsByBrandAndName("Samsung", "Samsung 1 Pro Watch")).thenReturn(List.of(product));

        //http://localhost:9193/api/v1/products/product/by-brand?brand=Apple

        ResultActions response = mockMvc.perform(get("/api/v1/products/product/by/brand-and-name")
                .contentType(MediaType.APPLICATION_JSON)
                .param("brandName", "Samsung")
                .param("productName", "Samsung 1 Pro Watch")
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}