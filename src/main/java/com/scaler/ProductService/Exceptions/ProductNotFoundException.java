package com.scaler.ProductService.Exceptions;

// Custom Checked Exception
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
