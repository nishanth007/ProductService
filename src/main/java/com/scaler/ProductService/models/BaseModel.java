package com.scaler.ProductService.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


public class BaseModel {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    BaseModel(){
        this.createdDate = new Date();
        this.lastUpdatedDate = this.createdDate;
    }

    private long id;
    private Date createdDate;
    private Date  lastUpdatedDate;
}
