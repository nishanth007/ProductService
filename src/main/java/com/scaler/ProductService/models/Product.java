package com.scaler.ProductService.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;

import java.util.Objects;

@Entity
public class Product extends BaseModel {

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(title, product.title) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", title='" + title + '\'' +
                ", category=" + category +
                '}';
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private Double price;
    private String title;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private Integer quantity;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

}
