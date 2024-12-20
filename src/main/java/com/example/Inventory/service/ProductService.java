package com.example.Inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventory.exception.ProductAlreadyExistsException;
import com.example.Inventory.exception.ProductNotFoundException;
import com.example.Inventory.model.Product;
import com.example.Inventory.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product){

        this.productRepository.findProductByName(product.getName())
            .orElseThrow(() -> new ProductAlreadyExistsException("Product Already Exists"));

        return this.productRepository.save(product);
    }

    public List<Product> listProduct(){
        return this.productRepository.findAll();
    }

    public Product listProductByName(String nameProduct){
        return this.listProductByName(nameProduct);
    }

    public Product updateProduct(Long idProduct, Product product){
        Product returnedProduct = this.productRepository.findById(idProduct)
            .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        returnedProduct.setName(product.getName());
        returnedProduct.setPrice(product.getPrice());
        returnedProduct.setDescription(product.getDescription());
        return this.productRepository.save(returnedProduct);
    }

    public String deleteProduct(Long idProduct){
        this.productRepository.deleteById(idProduct);
        return "Product was deleted with successfully";
    }

}
