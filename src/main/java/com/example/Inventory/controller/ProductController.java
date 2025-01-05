package com.example.Inventory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Inventory", description = "JWT Spring Boot API")
@RestController
@RequestMapping("/product")
public class ProductController {

}
