package com.scaler.ProductService.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Category extends BaseModel {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return Objects.equals(category, category1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(category);
    }

    @Override
    public String toString() {
        return "Category{" +
                "description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    private String description;

    @Column(unique = true)
    private String category;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // Foreign KeyConstrainsts does not allow deletion unless products are deleted or fk set to null;
    // Cannot delete or update a parent row: a foreign key constraint fails
    // So use cascading here
    // By default collections are fetched in lazy mode
    // Use @Transactional while doing lazy fetch
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category" , cascade = CascadeType.REMOVE)
    List<Product> products;
//    @OneToMany and @ManyToOne by default use lazy loading.
//    @ManyToMany and @OneToOne by default use eager loading.
}
