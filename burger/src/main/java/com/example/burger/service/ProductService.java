package com.example.burger.service;

import com.example.burger.model.dto.ProductDto;
import com.example.burger.model.entity.Product;
import com.example.burger.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map((product) -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found :" +id));
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        return modelMapper.map(productRepository.save(product),ProductDto.class);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto){
        Product product = productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found :"));
        modelMapper.map(productDto, Product.class );
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
