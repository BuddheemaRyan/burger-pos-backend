package com.example.burger.controller;

import com.example.burger.model.dto.OrderRequestDto;
import com.example.burger.model.dto.OrderResponseDto;
import com.example.burger.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@CrossOrigin(origins ="http://localhost:4200")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public OrderResponseDto placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.placeOrder(orderRequestDto);
    }
}
