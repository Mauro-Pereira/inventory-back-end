package com.example.Inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventory.DTO.Mapper;
import com.example.Inventory.DTO.ProductRequest;
import com.example.Inventory.exception.ProductAlreadyExistsException;
import com.example.Inventory.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Inventory", description = "JWT Spring Boot API")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest){
        try {
            return new ResponseEntity<>(this.productService.addProduct(Mapper.toProduct(productRequest)), HttpStatus.OK);
        } catch (ProductAlreadyExistsException e) {
            throw e;
        }
    }

}
