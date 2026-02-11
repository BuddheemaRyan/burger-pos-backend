package com.example.burger.model.dto;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private ProductDto product;
    private int quantity;
    private double price;
}
