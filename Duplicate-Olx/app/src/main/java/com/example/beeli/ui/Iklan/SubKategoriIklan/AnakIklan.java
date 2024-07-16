package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListAnakIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiBonekaMainan;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiBukuAnak;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiLain;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiPakaian;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiPerlengkapanBayi;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiPerlengkapanIbuBayi;
import com.example.beeli.ui.Iklan.FormIklan.Anak.FormIklanBayiStroller;

public class AnakIklan extends AppCompatActivity {
    public static Activity subAnak;
    SubListAnakIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SubListAnakIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        setOnClickListener(binding.aPakaian, FormIklanBayiPakaian.class);
        setOnClickListener(binding.apBayi, FormIklanBayiPerlengkapanBayi.class);
        setOnClickListener(binding.apIbu, FormIklanBayiPerlengkapanIbuBayi.class);
        setOnClickListener(binding.aToy, FormIklanBayiBonekaMainan.class);
        setOnClickListener(binding.aBuku, FormIklanBayiBukuAnak.class);
        setOnClickListener(binding.aStroller, FormIklanBayiStroller.class);
        setOnClickListener(binding.aOthers, FormIklanBayiLain.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(AnakIklan.this, cls);
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
