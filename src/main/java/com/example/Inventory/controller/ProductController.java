package com.example.Inventory.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventory.DTO.Mapper;
import com.example.Inventory.DTO.ProductRequest;
import com.example.Inventory.DTO.ProductResponse;
import com.example.Inventory.exception.ProductAlreadyExistsException;
import com.example.Inventory.exception.ProductCanNotBeLessThanZeroException;
import com.example.Inventory.exception.ProductNotFoundException;
import com.example.Inventory.model.Product;
import com.example.Inventory.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Inventory", description = "JWT Spring Boot API")
@RestController
@RequestMapping("/product")
@CrossOrigin(
    origins = "http://localhost:4200", 
    allowedHeaders = "*", 
    allowCredentials = "true", 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class ProductController {

    @Autowired
    private ProductService productService;


    @Operation(
        summary = "ADD Product",
        description = "A Product must be registered in the system with their given name, quantity, price, and a description.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registered with successfully"),
        @ApiResponse(responseCode = "409", description = "User Already Exists")
    })
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest){
        try {
            Product product = this.productService.addProduct(Mapper.toProduct(productRequest));
            return new ResponseEntity<>(Mapper.toProductResponse(product), HttpStatus.OK);
        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.status(409).build();
        }catch(ProductCanNotBeLessThanZeroException e){
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(
        summary = "Update Product",
        description = "A product must be updated in the system with their given name, quantity, price, and a description.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated with successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ProductResponse> updateUser(@PathVariable Long id, @RequestBody ProductRequest userResquest) {
        try {

            Product updatedProduct = this.productService.updateProduct(id, Mapper.toProduct(userResquest));
            return new ResponseEntity<>(Mapper.toProductResponse(updatedProduct), HttpStatus.OK);
            
        } catch (ProductCanNotBeLessThanZeroException e) {
            return ResponseEntity.status(500).build();
        }

    }

    @Operation(
        summary = "Return Product by Name",
        description = "A product must be returned from the system when a name is passed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returned with successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/getProductByName/{productName}")
    public ResponseEntity<ProductResponse> getUser(@PathVariable String productName) {

        try {
            
            Product returnedProduct = this.productService.findProductByName(productName);
            return new ResponseEntity<>(Mapper.toProductResponse(returnedProduct), HttpStatus.OK);
            
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(409).build();
        }

    }

    @Operation(
        summary = "List all Products",
        description = "All products must be returned from the system")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Returned with successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/listProduct")
    public ResponseEntity<?> listProduct(){
        try {
             List<ProductResponse> productResponseList = this.productService
                                            .listProduct()
                                            .stream()
                                            .map(Mapper::toProductResponse)
                                            .collect(Collectors.toList()
                                            );

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(
        summary = "Delete Product",
        description = "A product must be deleted from the system when a id is passed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted with successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {
            this.productService.deleteProduct(id);
            return ResponseEntity.status(200).build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }


}
