package com.scaler.ProductService.repository;


import com.scaler.ProductService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Override
    Optional<Product> findById(Long aLong);

    List<Product> findByTitle(String title);
}
