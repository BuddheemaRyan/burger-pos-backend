package com.example.burger.controller;

import com.example.burger.model.dto.ProductDto;
import com.example.burger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@CrossOrigin(origins ="http://localhost:4200")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public List<ProductDto> getAll() {
        return productService.findAll();
    }

    @GetMapping("/get/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
