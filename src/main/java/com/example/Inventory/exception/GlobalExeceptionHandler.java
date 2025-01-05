package com.example.Inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(ProductNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ProductAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(ProductAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ProductCanNotBeLessThanZeroException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(ProductCanNotBeLessThanZeroException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }


}
