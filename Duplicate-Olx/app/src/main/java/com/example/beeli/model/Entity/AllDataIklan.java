package com.example.beeli.model.Entity;

public class AllDataIklan {
    private String kondisi;
    private String judul_iklan;
    private String deskripsi;
    private int harga;
    private String name;
    private String alamat;

    private String tipe;
    private String merk;
    private String gaji_dari;
    private String gaji_sampai;
    private String tahun;
    private String tipe_bahan_bakar;
    private String warna;
    private String luas_bangunan;
    private String luas_tanah;
    private String kamar_tidur;
    private String kamar_mandi;
    private String lantai;
    private String fasilitas;
    private String alamat_lokasi;
    private String sertifikasi;


    public String getKondisi() {
        return kondisi;
    }

    public String getJudul_iklan() {
        return judul_iklan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public String getName() {
        return name;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTipe() {
        return tipe;
    }

    public String getMerk() {
        return merk;
    }

    public String getGaji_dari() {
        return gaji_dari;
    }

    public String getGaji_sampai() {
        return gaji_sampai;
    }

    public String getTahun() {
        return tahun;
    }

    public String getTipe_bahan_bakar() {
        return tipe_bahan_bakar;
    }

    public String getWarna() {
        return warna;
    }

    public String getLuas_bangunan() {
        return luas_bangunan;
    }

    public String getLuas_tanah() {
        return luas_tanah;
    }

    public String getKamar_tidur() {
        return kamar_tidur;
    }

    public String getKamar_mandi() {
        return kamar_mandi;
    }

    public String getLantai() {
        return lantai;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public String getAlamat_lokasi() {
        return alamat_lokasi;
    }

    public String getSertifikasi() {
        return sertifikasi;
    }

    @Override
    public String toString() {
        return "AllDataIklan{" +
                "kondisi='" + kondisi + '\'' +
                ", judul_iklan='" + judul_iklan + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", harga=" + harga +
                ", name='" + name + '\'' +
                ", alamat='" + alamat + '\'' +
                ", tipe='" + tipe + '\'' +
                ", merk='" + merk + '\'' +
                ", gaji_dari='" + gaji_dari + '\'' +
                ", gaji_sampai='" + gaji_sampai + '\'' +
                ", tahun='" + tahun + '\'' +
                ", tipe_bahan_bakar='" + tipe_bahan_bakar + '\'' +
                ", warna='" + warna + '\'' +
                ", luas_bangunan='" + luas_bangunan + '\'' +
                ", luas_tanah='" + luas_tanah + '\'' +
                ", kamar_tidur='" + kamar_tidur + '\'' +
                ", kamar_mandi='" + kamar_mandi + '\'' +
                ", lantai='" + lantai + '\'' +
                ", fasilitas='" + fasilitas + '\'' +
                ", alamat_lokasi='" + alamat_lokasi + '\'' +
                ", sertifikasi='" + sertifikasi + '\'' +
                '}';
    }
}
