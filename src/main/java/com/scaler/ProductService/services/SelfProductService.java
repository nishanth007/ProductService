package com.scaler.ProductService.services;

import com.scaler.ProductService.Exceptions.ProductNotFoundException;
import com.scaler.ProductService.models.Category;
import com.scaler.ProductService.models.Product;
import com.scaler.ProductService.repository.CategoryRepository;
import com.scaler.ProductService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class SelfProductService implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product p = (Product) redisTemplate.opsForHash().get("product", "PRODUCT_" + productId);

        if (p != null) {
            return p;
        }

        Optional<Product> product = productRepository.findById(productId);

        redisTemplate.opsForHash().put("product", "PRODUCT_" + productId, product.orElseThrow());

        return product.orElseThrow(() -> new ProductNotFoundException("Invalid product id"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Product createProduct(Product product) {

//        Category category = product.getCategory();
//        Optional<Category>  dbCat = categoryRepository.findByCategory(category.getCategory());
//        if(dbCat.isEmpty()) {
//            category = categoryRepository.save(category);
//            product.setCategory(category);
//        }
//        else{
//            product.setCategory(dbCat.get());
//        }

        /* with Cascade.persist we need not handle internal objects
        {
            "error": "Internal Server Error Overridden Global Advice",
            "message": "org.hibernate.TransientObjectException: persistent
             instance references an unsaved transient instance of 'com.scaler.ProductService.models.Category' (save the transient instance before flushing)",
            "status": 500
        }
        * */

        product = productRepository.save(product);
        return product;
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws ProductNotFoundException {
        Optional<Product> dbProduct = productRepository.findById(productId);
        if (!dbProduct.isEmpty()) {
            product.setId(productId);
            Category category = product.getCategory();

            if (category == null || (category.getId() == null && category.getCategory() == null))
                throw new ProductNotFoundException(" Category field is required");

            Optional<Category> dbCat =
                    category.getCategory() != null ?
                            categoryRepository.findByCategory(category.getCategory()) :
                            categoryRepository.findById(category.getId());

            if (!dbCat.isEmpty()) {
                product.setCategory(dbCat.get());
            } else {
                // new category
                category = categoryRepository.save(category);
                product.setCategory(category);
            }
        } else {
            throw new ProductNotFoundException("");
        }
        //Using product as param beacuse all  values should be updated
        product = productRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {

        Optional<Product> dbProduct = productRepository.findById(productId);


        if (!dbProduct.isEmpty()) {
            if (product.getTitle() != null)
                dbProduct.get().setTitle(product.getTitle());
            if (product.getPrice() != null) {
                dbProduct.get().setPrice(product.getPrice());
            }

            Category category = product.getCategory();

            if (category != null && (category.getCategory() == null && category.getId() == null))
                throw new ProductNotFoundException(" Category field is required");

            if (category != null) {
                Optional<Category> dbCat =
                        category.getCategory() != null ?
                                categoryRepository.findByCategory(category.getCategory()) :
                                categoryRepository.findById(category.getId());

                if (dbCat.isEmpty()) {
                    // new category
                    category = categoryRepository.save(category);
                    dbProduct.get().setCategory(category);
                }

            }
            // else no need to create one
            // Never update category values from product operation
            // you can only update category as a field of product


        } else {
            throw new ProductNotFoundException("");
        }
        //Using dbProduct beacuse all other values should remain same

        product = productRepository.save(dbProduct.get());

        return product;

    }
}
