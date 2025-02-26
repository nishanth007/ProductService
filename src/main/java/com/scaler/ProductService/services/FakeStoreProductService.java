package com.scaler.ProductService.services;

import com.scaler.ProductService.Exceptions.ProductNotFoundException;
import com.scaler.ProductService.dtos.FakeStoreProductDTO;
import com.scaler.ProductService.models.Category;
import com.scaler.ProductService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements ProductService {

    @Autowired
    private RestTemplate restTemplate;
    //private static Random  rd = new Random(); // creating Random object


    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        //Fake Product Service
        FakeStoreProductDTO fakeStoreProductDTO = (FakeStoreProductDTO) restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDTO.class);
//        if(true)
//            throw new RuntimeException("asdasd");
        if (fakeStoreProductDTO == null)
            throw new ProductNotFoundException(" Invalid product id");

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
        category.setId(fakeStoreProductDTO.getId() + 100);
        category.setCategory(fakeStoreProductDTO.getCategory());
        category.setDescription(fakeStoreProductDTO.getDescription());
        return product;
    }

    private static FakeStoreProductDTO convertRealProductToFakeStoreProduct(Product productDTO) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setCategory(productDTO.getCategory().getCategory());
        fakeStoreProductDTO.setPrice(productDTO.getPrice());
        fakeStoreProductDTO.setId(productDTO.getId());
        fakeStoreProductDTO.setTitle(productDTO.getTitle());
        fakeStoreProductDTO.setDescription(productDTO.getCategory().getDescription());
        return fakeStoreProductDTO;
    }

    @Override
    public List<Product> getAllProducts() {
        //Fake Product Service
        FakeStoreProductDTO[] fakeStoreProductDTOArray = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreProductDTO[].class);
        List<Product> listOfProducts = null;
        if (fakeStoreProductDTOArray != null)
            listOfProducts = Arrays.stream(fakeStoreProductDTOArray).map(e -> (Product) convertFakeStoreProductToRealProduct(e)).collect(Collectors.toList());

        return listOfProducts;
    }

    @Override
    public Page<Product> getAllProducts(int PageNumber, int PageSize) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        HttpMethod method = HttpMethod.PATCH;
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.patchForObject("https://fakestoreapi.com/products/" + productId, convertRealProductToFakeStoreProduct(product), FakeStoreProductDTO.class);
        assert fakeStoreProductDTO != null;
        return convertFakeStoreProductToRealProduct(fakeStoreProductDTO);

    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        HttpMethod method = HttpMethod.PUT;
        return callRestTemplateByHttpMethod(productId, product, method);

    }

    private Product callRestTemplateByHttpMethod(Long productId, Product product, HttpMethod method) {

        FakeStoreProductDTO fakeStoreProductDTO = convertRealProductToFakeStoreProduct(product);
        System.out.println(fakeStoreProductDTO.toString());
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDTO, FakeStoreProductDTO.class);

        HttpMessageConverterExtractor<FakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor<>(FakeStoreProductDTO.class, restTemplate.getMessageConverters());

        return convertFakeStoreProductToRealProduct((FakeStoreProductDTO) Objects.requireNonNull(restTemplate.execute("https://fakestoreapi.com/products/" + productId, method, requestCallback, responseExtractor)));
    }
}
