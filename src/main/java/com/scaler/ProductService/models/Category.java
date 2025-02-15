package com.scaler.ProductService.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Category extends BaseModel {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    private String description;
    private String category;
}
