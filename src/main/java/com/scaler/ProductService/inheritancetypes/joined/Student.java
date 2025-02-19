package com.scaler.ProductService.inheritancetypes.joined;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jt_student")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    Long stu_id;
    String batch;

    public Long getStu_id() {
        return stu_id;
    }

    public void setStu_id(Long stu_id) {
        this.stu_id = stu_id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
