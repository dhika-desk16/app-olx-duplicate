package com.example.beeli.model;

import java.io.Serializable;

public class RegenciesModel implements Serializable {
    private String id;
    private String province_id;
    private String name;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceId() {
        return province_id;
    }

    public void setProvinceId(String province_id) {
        this.province_id = province_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
