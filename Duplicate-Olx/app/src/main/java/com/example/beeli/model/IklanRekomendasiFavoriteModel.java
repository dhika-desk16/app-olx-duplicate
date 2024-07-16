package com.example.beeli.model;

import com.example.beeli.model.Entity.AllDataIklan;
import com.example.beeli.model.Entity.DataGambar12;
import com.example.beeli.model.Entity.IdentityIklan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IklanRekomendasiFavoriteModel {
    private boolean success;
    private List<IklanFavorit> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IklanFavorit> getData() {
        return data;
    }

    public static class IklanFavorit {
        @SerializedName("identity_iklan")
        private IdentityIklan identityIklan;
        @SerializedName("data_iklan")
        private AllDataIklan dataIklan;
        @SerializedName("data_gambar")
        private DataGambar12 dataGambar;

        public IdentityIklan getIdentityIklan() {
            return identityIklan;
        }

        public void setIdentityIklan(IdentityIklan identityIklan) {
            this.identityIklan = identityIklan;
        }

        public AllDataIklan getDataIklan() {
            return dataIklan;
        }

        public void setDataIklan(AllDataIklan dataIklan) {
            this.dataIklan = dataIklan;
        }

        public DataGambar12 getDataGambar() {
            return dataGambar;
        }

        public void setDataGambar(DataGambar12 dataGambar) {
            this.dataGambar = dataGambar;
        }

        @Override
        public String toString() {
            return "IklanFavorit{" +
                    "identityIklan=" + identityIklan +
                    ", dataIklan=" + dataIklan +
                    ", dataGambar=" + dataGambar +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IklanFavoriteModel{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}