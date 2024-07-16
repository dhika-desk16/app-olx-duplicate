package com.example.beeli.model.Entity;

public class DataIklanSJasa {
    private String tipe;
    private String gaji_dari;
    private String gaji_sampai;
    private String judul_iklan;
    private String deskripsi;
    private String name;
    private String alamat;

    public String getJudul_iklan() {
        return judul_iklan;
    }

    public void setJudul_iklan(String judul_iklan) {
        this.judul_iklan = judul_iklan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getGaji_dari() {
        return gaji_dari;
    }

    public void setGaji_dari(String gaji_dari) {
        this.gaji_dari = gaji_dari;
    }

    public String getGaji_sampai() {
        return gaji_sampai;
    }

    public void setGaji_sampai(String gaji_sampai) {
        this.gaji_sampai = gaji_sampai;
    }
}
