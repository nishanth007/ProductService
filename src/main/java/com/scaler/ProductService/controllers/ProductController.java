package com.scaler.ProductService.controllers;

import com.scaler.ProductService.models.Product;
import com.scaler.ProductService.services.FakeStoreProductService;
import com.scaler.ProductService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/{id}" , produces=MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable("id") Long id){
      return productService.getSingleProduct(id);
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
