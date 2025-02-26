package com.scaler.ProductService.controllers;

import com.scaler.ProductService.Exceptions.ProductNotFoundException;
import com.scaler.ProductService.models.Product;
import com.scaler.ProductService.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    // @Qualifier("SelfProductService"); or use @Primary on the SelfProductService
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(id);

        // Create HATEOAS links
        EntityModel<Product> resource = EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateProduct(id, product)).withRel("update")
        );

        return ResponseEntity.ok(resource);
    }


    private static <T> ResponseEntity<T> prepareSimpleResponseEntity(T product, HttpStatus status) {
        return new ResponseEntity<>(product, status);

    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/paged")
    public Page<Product> getAllProducts(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return productService.getAllProducts(pageNumber, pageSize);
    }


    //PATCH call - Partial Update
    @PatchMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product) throws ProductNotFoundException {
        return productService.updateProduct(productId, product);
    }


    //PUT call - replaces
    @PutMapping("/{productId}")
    public Product replaceProduct(@PathVariable("productId") Long productId, @RequestBody Product product) throws ProductNotFoundException {
        return productService.replaceProduct(productId, product);
    }

    @PostMapping("/")
    public Product replaceProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Item deleted successfully");
    }

    // Handler moved to Global exception handler
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "ProductNotFoundException");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
