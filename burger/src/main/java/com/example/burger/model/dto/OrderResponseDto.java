package com.example.burger.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public  class OrderResponseDto {
    private Long id;
    private LocalDateTime createdAt;
    private double total;
    private String status;
    private List<OrderItemResponseDto> items;
}
