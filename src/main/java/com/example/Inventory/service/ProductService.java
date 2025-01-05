package com.example.Inventory.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventory.Message;
import com.example.Inventory.exception.ProductAlreadyExistsException;
import com.example.Inventory.exception.ProductCanNotBeLessThanZeroException;
import com.example.Inventory.exception.ProductNotFoundException;
import com.example.Inventory.model.Product;
import com.example.Inventory.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product){

        this.productRepository.findProductByName(product.getName())
            .ifPresent(pruduct -> {
                throw new ProductAlreadyExistsException("Product already exists");
            });

        validateProductFields(product);


        return this.productRepository.save(product);
    }

    public Product increaseQuantity(Product product){
        Product returnedProduct = this.productRepository.findById(product.getId())
                        .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        returnedProduct.setQuantity(returnedProduct.getQuantity() < 0 ? 0 + 1: returnedProduct.getQuantity() + 1);
        return returnedProduct;
    }

    public Product decreaseQuantity(Product product){
        Product returnedProduct = this.productRepository.findById(product.getId())
        .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        if(returnedProduct.getQuantity() < 0){
            throw new ProductCanNotBeLessThanZeroException("Produc can not be less than zero");
        }

        if(returnedProduct.getQuantity() <= 0){
            returnedProduct.setQuantity(0);
            return returnedProduct;
        }

        Integer quantity = returnedProduct.getQuantity() - 1;
        returnedProduct.setQuantity(quantity);
        return returnedProduct;
    }

    public List<Product> listProduct(){
        return this.productRepository.findAll();
    }

    public Product findProductByName(String nameProduct){
        Product product = this.productRepository.findProductByName(nameProduct)
                               .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        return product;
    }

    public Product updateProduct(Long idProduct, Product product){
        Product returnedProduct = this.productRepository.findById(idProduct)
            .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        validateProductFields(product);

        if (StringUtils.isNotBlank(product.getName())) {
            returnedProduct.setName(product.getName());
        }
        
        if(product.getPrice() != null || product.getPrice() >= 0){
            returnedProduct.setPrice(product.getPrice());
        }
       
        if(product.getDescription() != null || product.getDescription() != ""){
            returnedProduct.setDescription(product.getDescription());
        }

        if(product.getQuantity() != null){
            returnedProduct.setQuantity(product.getQuantity());
        }
        
        return this.productRepository.save(returnedProduct);
    }

    private void validateProductFields(Product product) {
        if (product.getQuantity() < 0) {
            throw new ProductCanNotBeLessThanZeroException("The quantity of the product can not be less than zero!");
        }

        if (product.getPrice() < 0) {
            throw new ProductCanNotBeLessThanZeroException("The price of the product can not be less than zero!");
        }
    }

    public Message deleteProduct(Long idProduct){
        this.productRepository.deleteById(idProduct);
        Message message = new Message("Product was deleted with successfully");
        return message;
    }

    public boolean missingProduct(Product product){
        return product.getQuantity() == 0 ? true: false; 
    }

    public boolean lowStockProduct(Product product){
        return product.getQuantity() < 10 ? true: false;
    }

    public double stockValue(Product product){
        return product.getQuantity() * product.getPrice();
    }

}
