package com.scaler.ProductService.services;

import com.scaler.ProductService.dtos.FakeStoreProductDTO;
import com.scaler.ProductService.models.Category;
import com.scaler.ProductService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements  ProductService {

    @Autowired
    private RestTemplate restTemplate;
    //private static Random  rd = new Random(); // creating Random object


    @Override
    public Product getSingleProduct(Long productId) {

        //Fake Product Service
        FakeStoreProductDTO fakeStoreProductDTO = (FakeStoreProductDTO) restTemplate.getForObject("https://fakestoreapi.com/products/" + productId , FakeStoreProductDTO.class);
        assert fakeStoreProductDTO != null;

        return convertFakeStoreProductToRealProduct(fakeStoreProductDTO);
    }

    private static Product convertFakeStoreProductToRealProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        Category category = new Category();
        product.setCategory(category);
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
       // category.setId((long) rd.nextLong());;
        category.setId(fakeStoreProductDTO.getId() +100);
        category.setCategory(fakeStoreProductDTO.getCategory());
        category.setDescription(fakeStoreProductDTO.getDescription());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        //Fake Product Service
        FakeStoreProductDTO[] fakeStoreProductDTOArray = restTemplate.getForObject("https://fakestoreapi.com/products/"  , FakeStoreProductDTO[].class);
        List<Product> listOfProducts= null;
        if(fakeStoreProductDTOArray !=null)
            listOfProducts =  Arrays.stream(fakeStoreProductDTOArray).map( e -> (Product) convertFakeStoreProductToRealProduct(e)).collect(Collectors.toList());

        return listOfProducts;
    }
}
