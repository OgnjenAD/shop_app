package com.dailycodework.dream_shops.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
   // private String password;

    private CartDto cart;
    private List<OrderDto> order;

}
