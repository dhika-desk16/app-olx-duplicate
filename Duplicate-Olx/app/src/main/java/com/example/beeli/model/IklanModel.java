package com.example.beeli.model;

import com.example.beeli.model.Entity.DataGambar12;
import com.example.beeli.model.Entity.DataIklan;
import com.example.beeli.model.Entity.IdentityIklan;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class IklanModel {
    private boolean success;
    private List<Map<String, Iklan>> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Map<String, Iklan>> getData() {
        return data;
    }

    public void setData(List<Map<String, Iklan>> data) {
        this.data = data;
    }

    //    this the one should be data_iklan_1, 2, 3,...
    public static class Iklan {
        @SerializedName("identity_iklan")
        private IdentityIklan identityIklan;
        @SerializedName("data_iklan")
        private DataIklan dataIklan;
        @SerializedName("data_gambar")
        private DataGambar12 dataGambar;

        public IdentityIklan getIdentityIklan() {
            return identityIklan;
        }

        public void setIdentityIklan(IdentityIklan identityIklan) {
            this.identityIklan = identityIklan;
        }

        public DataIklan getDataIklan() {
            return dataIklan;
        }

        public void setDataIklan(DataIklan dataIklan) {
            this.dataIklan = dataIklan;
        }

        public DataGambar12 getDataGambar() {
            return dataGambar;
        }

        public void setDataGambar(DataGambar12 dataGambar) {
            this.dataGambar = dataGambar;
        }
    }
}