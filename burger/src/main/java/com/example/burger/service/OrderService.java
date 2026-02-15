package com.example.burger.service;

import com.example.burger.model.dto.OrderItemRequestDto;
import com.example.burger.model.dto.OrderRequestDto;
import com.example.burger.model.dto.OrderResponseDto;
import com.example.burger.model.dto.ProductDto;
import com.example.burger.model.entity.Order;
import com.example.burger.model.entity.OrderItem;
import com.example.burger.model.entity.Product;
import com.example.burger.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductService productService;


    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        double total = 0;

        for (OrderItemRequestDto reqItem : orderRequestDto.getItems()) {
            ProductDto productDto = productService.findById(reqItem.getProductId());
            Product product = modelMapper.map(productDto, Product.class);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(reqItem.getQuantity());
            orderItem.setPrice(productDto.getPrice());

            order.getItems().add(orderItem);
            total += productDto.getPrice() * reqItem.getQuantity();
        }
        order.setTotal(total);
        order.setStatus("PENDING");

        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderResponseDto.class);
    }

}
