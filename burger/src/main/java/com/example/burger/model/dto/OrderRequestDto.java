package com.example.burger.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderItemRequestDto> items;

}
