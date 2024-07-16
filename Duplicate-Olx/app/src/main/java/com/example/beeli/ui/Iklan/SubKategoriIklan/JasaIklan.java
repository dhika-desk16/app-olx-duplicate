package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListJasaIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.Jasa.FormIklanJasa;
import com.example.beeli.ui.Iklan.FormIklan.Jasa.FormIklanJasaLowongan;
import com.example.beeli.ui.Iklan.FormIklan.Jasa.FormIklanJasaPekerjaan;

public class JasaIklan extends AppCompatActivity {
    public static Activity subJasa;
    SubListJasaIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListJasaIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.jLowongan, FormIklanJasaLowongan.class);
        setOnClickListener(binding.jKerja, FormIklanJasaPekerjaan.class);
        setOnClickListener(binding.jJasa, FormIklanJasa.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(JasaIklan.this, cls);
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
