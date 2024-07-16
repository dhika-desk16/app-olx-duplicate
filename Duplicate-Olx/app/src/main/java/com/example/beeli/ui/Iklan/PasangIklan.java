package com.example.beeli.ui.Iklan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.beeli.databinding.ActivityPasangIklanCategoryBinding;
import com.example.beeli.ui.Iklan.SubKategoriIklan.ElektronikIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.JasaIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.MobilIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.MotorIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.OlahragaIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.PropertiIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.RumahIklan;

public class PasangIklan extends AppCompatActivity {

    ActivityPasangIklanCategoryBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPasangIklanCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.cardViewMobil, MobilIklan.class);
        setOnClickListener(binding.cardViewProperti, PropertiIklan.class);
        setOnClickListener(binding.cardViewMotor, MotorIklan.class);
        setOnClickListener(binding.cardViewJasaDanLowongan, JasaIklan.class);
        setOnClickListener(binding.cardViewElektronikDanGadget, ElektronikIklan.class);
        setOnClickListener(binding.cardViewHobiDanOlahraga, OlahragaIklan.class);
        setOnClickListener(binding.cardViewRumahTangga, RumahIklan.class);
        setOnClickListener(binding.cardViewOpsiLainnya, PilihKategoriIklan.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(PasangIklan.this, cls);
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
