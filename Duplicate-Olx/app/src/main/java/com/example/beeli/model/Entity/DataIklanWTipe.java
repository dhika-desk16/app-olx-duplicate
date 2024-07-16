package com.example.beeli.model.Entity;

public class DataIklanWTipe {
    private String tipe;
    private String kondisi;
    private String judul_iklan;
    private String deskripsi;
    private int harga;
    private String name;
    private String alamat;

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

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

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
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
}