package com.example.beeli.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VillageModel implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("district_id")
    private int district_id;

    @SerializedName("name")
    private String name;

    public VillageModel(long id, int district_id, String name) {
        this.id = id;
        this.district_id = district_id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
