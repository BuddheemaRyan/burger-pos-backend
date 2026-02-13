package com.example.burger.controller;

import com.example.burger.model.dto.OrderRequestDto;
import com.example.burger.model.dto.OrderResponseDto;
import com.example.burger.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")

public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public OrderResponseDto placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.placeOrder(orderRequestDto);
    }
}
