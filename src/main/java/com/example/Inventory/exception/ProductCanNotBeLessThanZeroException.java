package com.example.Inventory.exception;

public class ProductCanNotBeLessThanZeroException extends RuntimeException{

    public ProductCanNotBeLessThanZeroException(String message){
        super(message);
    }

}
