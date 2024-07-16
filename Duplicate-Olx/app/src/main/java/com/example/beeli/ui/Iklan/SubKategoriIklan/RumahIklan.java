package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListRumahIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaDekorRumah;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaJam;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaKonstruksiDanTaman;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaLain;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaLampu;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaMakMin;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaMebel;
import com.example.beeli.ui.Iklan.FormIklan.RumahTangga.FormIklanRumahTanggaPerlengkapanRumah;

public class RumahIklan extends AppCompatActivity {
    public static Activity subRumah;
    SubListRumahIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListRumahIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.rMakanan, FormIklanRumahTanggaMakMin.class);
        setOnClickListener(binding.rMebel, FormIklanRumahTanggaMebel.class);
        setOnClickListener(binding.rDekor, FormIklanRumahTanggaDekorRumah.class);
        setOnClickListener(binding.rTaman, FormIklanRumahTanggaKonstruksiDanTaman.class);
        setOnClickListener(binding.rJam, FormIklanRumahTanggaJam.class);
        setOnClickListener(binding.rLampu, FormIklanRumahTanggaLampu.class);
        setOnClickListener(binding.rPerlengkapan, FormIklanRumahTanggaPerlengkapanRumah.class);
        setOnClickListener(binding.rOthers, FormIklanRumahTanggaLain.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(RumahIklan.this, cls);
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
