package com.scaler.ProductService;

import com.scaler.ProductService.models.Product;
import com.scaler.ProductService.repository.CategoryRepository;
import com.scaler.ProductService.repository.ProductRepository;
import com.scaler.ProductService.repository.projections.ProductsWithTitleAndPriceById;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //@Test
    void contextLoads() {
    }

    //@Test
    void check() {
        Optional<Product> byId = productRepository.findById(5L);
        System.out.println(byId.get().getTitle());
        List<ProductsWithTitleAndPriceById> productsByTitlePriceById = productRepository.getProductsByTitlePriceById(5L);
        productsByTitlePriceById.forEach(item -> System.out.println(item.getTitle()));
    }

    //@Test
    void checkcascadeDelete() {
        categoryRepository.deleteById(1L);
    }

    // @Test
    void checkfetch() {
        categoryRepository.findById(3L);
    }

}
