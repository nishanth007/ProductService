package com.scaler.ProductService.services;

import com.scaler.ProductService.models.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId);

    List<Product> getAllProducts();

}
