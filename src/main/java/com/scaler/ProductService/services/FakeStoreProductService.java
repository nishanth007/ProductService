package com.scaler.ProductService.services;

import com.scaler.ProductService.dtos.FakeStoreProductDTO;
import com.scaler.ProductService.models.Category;
import com.scaler.ProductService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements  ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getSingleProduct(Long productId) {
        Product product = new Product();
        Category category = new Category();
        product.setCategory(category);

        //Fake Product Service
        FakeStoreProductDTO fakeStoreProductDTO = (FakeStoreProductDTO) restTemplate.getForObject("https://fakestoreapi.com/products/" + productId , FakeStoreProductDTO.class);

        assert fakeStoreProductDTO != null;
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        category.setCategory(fakeStoreProductDTO.getCategory());
        category.setDescription(fakeStoreProductDTO.getDescription());
        RestTemplate restTemplate = new RestTemplate();
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
