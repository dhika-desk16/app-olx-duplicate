package com.example.beeli.model;

import com.example.beeli.model.Entity.AllDataIklan;
import com.example.beeli.model.Entity.DataGambar12;
import com.example.beeli.model.Entity.IdentityIklan;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IklanSayaModel {
    private boolean success;
    private IklanSaya data_iklan;

    public boolean isSuccess() {
        return success;
    }

    public IklanSaya getData_iklan() {
        return data_iklan;
    }

    public static class IklanSaya {
        private List<Map<String, Iklan>> iklan_JasaDanLowongan;
        private List<Map<String, Iklan>> iklan_mobil;
        private List<Map<String, Iklan>> iklan_motor;
        private List<Map<String, Iklan>> iklan_Properti;
        private List<Map<String, Iklan>> iklan_RumahTangga;
        private List<Map<String, Iklan>> iklan_PerlengkapanBayiDanAnak;
        private List<Map<String, Iklan>> iklan_elektronik;
        private List<Map<String, Iklan>> iklan_KantorDanIndustri;
        private List<Map<String, Iklan>> iklan_hobiDanOlahraga;
        private List<Map<String, Iklan>> iklan_KeperluanPribadi;

        public List<Map<String, Iklan>> getIklan_JasaDanLowongan() {
            return iklan_JasaDanLowongan;
        }

        public List<Map<String, Iklan>> getIklan_mobil() {
            return iklan_mobil;
        }

        public List<Map<String, Iklan>> getIklan_motor() {
            return iklan_motor;
        }

        public List<Map<String, Iklan>> getIklan_Properti() {
            return iklan_Properti;
        }

        public List<Map<String, Iklan>> getIklan_RumahTangga() {
            return iklan_RumahTangga;
        }

        public List<Map<String, Iklan>> getIklan_PerlengkapanBayiDanAnak() {
            return iklan_PerlengkapanBayiDanAnak;
        }

        public List<Map<String, Iklan>> getIklan_elektronik() {
            return iklan_elektronik;
        }

        public List<Map<String, Iklan>> getIklan_KantorDanIndustri() {
            return iklan_KantorDanIndustri;
        }

        public List<Map<String, Iklan>> getIklan_hobiDanOlahraga() {
            return iklan_hobiDanOlahraga;
        }

        public List<Map<String, Iklan>> getIklan_KeperluanPribadi() {
            return iklan_KeperluanPribadi;
        }

        public List<List<Map<String, Iklan>>> getAllIklanLists() {
            List<List<Map<String, Iklan>>> allIklanLists = new ArrayList<>();
            allIklanLists.add(getIklan_JasaDanLowongan());
            allIklanLists.add(getIklan_mobil());
            allIklanLists.add(getIklan_motor());
            allIklanLists.add(getIklan_Properti());
            allIklanLists.add(getIklan_RumahTangga());
            allIklanLists.add(getIklan_PerlengkapanBayiDanAnak());
            allIklanLists.add(getIklan_elektronik());
            allIklanLists.add(getIklan_KantorDanIndustri());
            allIklanLists.add(getIklan_hobiDanOlahraga());
            allIklanLists.add(getIklan_KeperluanPribadi());
            return allIklanLists;
        }

        public static class Iklan {
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
        }
    }
}