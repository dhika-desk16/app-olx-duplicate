package com.example.beeli.model.Entity;

public class IdentityIklan {
    private String email;
    private String kode_iklan;
    private String kategori;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKode_iklan() {
        return kode_iklan;
    }

    public void setKode_iklan(String kode_iklan) {
        this.kode_iklan = kode_iklan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return "IdentityIklan{" +
                "email='" + email + '\'' +
                ", kode_iklan='" + kode_iklan + '\'' +
                ", kategori='" + kategori + '\'' +
                '}';
    }
}
