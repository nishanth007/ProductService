package com.scaler.ProductService.services;

import com.scaler.ProductService.Exceptions.ProductNotFoundException;
import com.scaler.ProductService.models.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    public Product createProduct(Product product);

    public void deleteProduct(Long productId);

    public Product updateProduct(Long productId,Product product) throws ProductNotFoundException;

    public Product replaceProduct(Long productId, Product product) throws ProductNotFoundException;
}
