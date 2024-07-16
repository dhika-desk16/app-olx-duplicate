package com.example.beeli.model.Entity;

public class DataIklanSMobilMotor {
    private String merk;
    private String kondisi;
    private String judul_iklan;
    private String tahun;
    private String tipe_bahan_bakar;
    private String warna;
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

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getTipe_bahan_bakar() {
        return tipe_bahan_bakar;
    }

    public void setTipe_bahan_bakar(String tipe_bahan_bakar) {
        this.tipe_bahan_bakar = tipe_bahan_bakar;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }
}
