package com.scaler.ProductService.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
public class BaseModel implements Serializable {

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    BaseModel() {
        this.createdDate = new Date();
        this.lastUpdatedDate = this.createdDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdDate;
    private Date lastUpdatedDate;
}
