package com.unimagdalena.android.app.domiciliosmilcarnes.model.entity;

import java.io.Serializable;

public class Plate implements Serializable {

    private String picture;
    private String name;
    private String description;
    private String type;
    private Integer qualification;
    private Long price;

    public Plate(String picture, String name, String description, String type, Integer qualification, Long price) {
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.type = type;
        this.qualification = qualification;
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQualification() {
        return qualification;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
