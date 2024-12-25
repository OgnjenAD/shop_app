package com.dailycodework.dream_shops.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private long categoryId;
    private String name;
    private List<ProductDto> products;
}
