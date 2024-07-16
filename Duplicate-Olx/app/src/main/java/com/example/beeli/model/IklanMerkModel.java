package com.example.beeli.model;

import com.example.beeli.model.Entity.DataGambar12;
import com.example.beeli.model.Entity.DataIklanWMerk;
import com.example.beeli.model.Entity.IdentityIklan;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class IklanMerkModel {
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
        private DataIklanWMerk dataIklan;
        @SerializedName("data_gambar")
        private DataGambar12 dataGambar;

        public IdentityIklan getIdentityIklan() {
            return identityIklan;
        }

        public void setIdentityIklan(IdentityIklan identityIklan) {
            this.identityIklan = identityIklan;
        }

        public DataIklanWMerk getDataIklan() {
            return dataIklan;
        }

        public void setDataIklan(DataIklanWMerk dataIklan) {
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