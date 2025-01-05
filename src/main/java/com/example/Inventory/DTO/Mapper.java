package com.example.Inventory.DTO;

import com.example.Inventory.model.Product;

public class Mapper {

    public static Product toProduct(ProductRequest productRequest){
        
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setQuantity(productRequest.quantity());
        product.setDescription(productRequest.description());
        return product;
    }

    public static ProductResponse toProductResponse(Product product){
       return new ProductResponse(product.getId(), product.getName(), product.getQuantity(), product.getPrice(), product.getDescription());
    }

}
