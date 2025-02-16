package com.scaler.ProductService.services;

import com.scaler.ProductService.Exceptions.ProductNotFoundException;
import com.scaler.ProductService.models.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    public void deleteProduct(Long productId);

    public Product updateProduct(Long productId,Product product);

    public Product replaceProduct(Long productId, Product product);
}
