package com.dailycodework.dream_shops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "carts", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartsItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;

    public void addItem(CartsItem item) {
        this.items.add(item);
        item.setCarts(this);
        updateTotalAmount();
    }

    public void removeItem(CartsItem item) {
        this.items.remove(item);
        item.setCarts(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = items.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitePrice();
            if (unitPrice == null) {
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
