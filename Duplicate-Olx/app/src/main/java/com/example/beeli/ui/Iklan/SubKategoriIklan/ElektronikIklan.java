package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListElektroIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiStroller;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikAksesoris;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikFotografi;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikGames;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikLampu;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikPhone;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikRumah;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikTV;
import com.example.beeli.ui.Iklan.FormIklan.Elektronik.FormIklanElektronikTablet;

public class ElektronikIklan extends AppCompatActivity {
    public static Activity subElektro;
    SubListElektroIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListElektroIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.ePhone, FormIklanElektronikPhone.class);
        setOnClickListener(binding.eTablet, FormIklanElektronikTablet.class);
        setOnClickListener(binding.eAksesoris, FormIklanElektronikAksesoris.class);
        setOnClickListener(binding.eFoto, FormIklanElektronikFotografi.class);
        setOnClickListener(binding.eRumah, FormIklanElektronikRumah.class);
        setOnClickListener(binding.eGames, FormIklanElektronikGames.class);
        setOnClickListener(binding.eKomputer, FormIklanBayiStroller.class);
        setOnClickListener(binding.eLampu, FormIklanElektronikLampu.class);
        setOnClickListener(binding.eTV, FormIklanElektronikTV.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(ElektronikIklan.this, cls);
            startActivity(intent);
        });
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
