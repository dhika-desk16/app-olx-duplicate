package com.example.beeli.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DistrictModel implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("regency_id")
    private String regencyId;

    @SerializedName("name")
    private String name;

    // Constructor
    public DistrictModel(String id, String regencyId, String name) {
        this.id = id;
        this.regencyId = regencyId;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegencyId() {
        return regencyId;
    }

    public void setRegencyId(String regencyId) {
        this.regencyId = regencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
