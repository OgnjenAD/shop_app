package com.dailycodework.dream_shops.request.product;

import com.dailycodework.dream_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

    public UpdateProductRequest(Long id,
                                String name,
                                String brand,
                                BigDecimal price,
                                int inventory,
                                String description,
                                Category category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
