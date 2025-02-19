package com.scaler.ProductService.repository;

import com.scaler.ProductService.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Override
    Optional<Category> findById(Long id);

    List<Category> findByDescription(String desc);

    Optional<Category> findByCategory(String category);
}
