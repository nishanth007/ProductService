package com.scaler.ProductService.repository;


import com.scaler.ProductService.models.Product;
import com.scaler.ProductService.repository.projections.ProductsWithTitleAndPriceById;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);

    List<Product> findByTitle(String title);

    @Query("select p.title as title , p.price as price from Product p where p.id = :pid")
    List<ProductsWithTitleAndPriceById> getProductsByTitlePriceById(@Param("pid") Long pid);

    Page<Product> findByTitle(String title, Pageable pageable);

    Page<Product> findAll(Pageable pageable);


}
