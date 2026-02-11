package com.example.burger.model.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long productId;
    private int quantity;
}
