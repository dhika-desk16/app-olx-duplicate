package com.example.beeli.model;

public class ProfileModel {
    private boolean success;
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class User{
        private int id;
        private String name;
        private String email;
        private String password;
        private String password_confirmation;
        private String num_phone;
        private String tentang_saya;
        private String pict_profile;
        private String alamat;
        private String regencies;
        private String districts;
        private String villages;
        private String provinces;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getPassword_confirmation() {
            return password_confirmation;
        }

        public void setPassword_confirmation(String password_confirmation) {
            this.password_confirmation = password_confirmation;
        }


        public String getNum_phone() {
            return num_phone;
        }

        public void setNum_phone(String num_phone) {
            this.num_phone = num_phone;
        }

        public String getTentang_saya() {
            return tentang_saya;
        }

        public void setTentang_saya(String tentang_saya) {
            this.tentang_saya = tentang_saya;
        }

        public String getPict_profile() {
            return pict_profile;
        }

        public void setPict_profile(String pict_profile) {
            this.pict_profile = pict_profile;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getRegencies() {
            return regencies;
        }

        public void setRegencies(String regencies) {
            this.regencies = regencies;
        }

        public String getDistricts() {
            return districts;
        }

        public void setDistricts(String districts) {
            this.districts = districts;
        }

        public String getVillages() {
            return villages;
        }

        public void setVillages(String villages) {
            this.villages = villages;
        }

        public String getProvinces() {
            return provinces;
        }

        public void setProvinces(String provinces) {
            this.provinces = provinces;
        }
    }
}