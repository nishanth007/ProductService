package com.scaler.ProductService.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Product extends BaseModel {

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", title='" + title + '\'' +
                ", category=" + category +
                '}';
    }

    public void setPrice(double price) {
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

    private double price;
    private String title;
    private Category category;
}
