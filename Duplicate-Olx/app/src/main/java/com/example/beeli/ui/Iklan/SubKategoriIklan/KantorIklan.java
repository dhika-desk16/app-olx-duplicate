package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListKantorIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.KantorIndustri.FormIklanKantorIndustriLain;
import com.example.beeli.ui.Iklan.FormIklan.KantorIndustri.FormIklanKantorIndustriMesinDanKeperluanIndustri;
import com.example.beeli.ui.Iklan.FormIklan.KantorIndustri.FormIklanKantorIndustriPeralatanKantor;
import com.example.beeli.ui.Iklan.FormIklan.KantorIndustri.FormIklanKantorIndustriPerlengkapanUsaha;
import com.example.beeli.ui.Iklan.FormIklan.KantorIndustri.FormIklanKantorIndustriStationary;

public class KantorIklan extends AppCompatActivity {
    public static Activity subKantor;
    SubListKantorIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListKantorIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.kaKantor, FormIklanKantorIndustriPeralatanKantor.class);
        setOnClickListener(binding.kaUsaha, FormIklanKantorIndustriPerlengkapanUsaha.class);
        setOnClickListener(binding.kaMesin, FormIklanKantorIndustriMesinDanKeperluanIndustri.class);
        setOnClickListener(binding.kaStationery, FormIklanKantorIndustriStationary.class);
        setOnClickListener(binding.kaOthers, FormIklanKantorIndustriLain.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(KantorIklan.this, cls);
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
