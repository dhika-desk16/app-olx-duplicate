package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiAlatMusik;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiBarangAntik;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiBukuDanMajalah;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiHandicraft;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiHewan;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiKoleksi;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiMainan;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiMusikDanFilm;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiOlahraga;
import com.example.beeli.ui.Iklan.FormIklan.HobiDanOlahraga.FormIklanHobiSepedaAksesoris;
import com.example.beeli.databinding.SubListOlahragaIklanBinding;

public class OlahragaIklan extends AppCompatActivity {
    public static Activity subOlahraga;
    SubListOlahragaIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SubListOlahragaIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        setOnClickListener(binding.oMusic, FormIklanHobiAlatMusik.class);
        setOnClickListener(binding.oOlahraga, FormIklanHobiOlahraga.class);
        setOnClickListener(binding.oSepeda, FormIklanHobiSepedaAksesoris.class);
        setOnClickListener(binding.oCrafts, FormIklanHobiHandicraft.class);
        setOnClickListener(binding.oAntik, FormIklanHobiBarangAntik.class);
        setOnClickListener(binding.oBuku, FormIklanHobiBukuDanMajalah.class);
        setOnClickListener(binding.oKoleksi, FormIklanHobiKoleksi.class);
        setOnClickListener(binding.oToy, FormIklanHobiMainan.class);
        setOnClickListener(binding.oFilm, FormIklanHobiMusikDanFilm.class);
        setOnClickListener(binding.oHewan, FormIklanHobiHewan.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(OlahragaIklan.this, cls);
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
