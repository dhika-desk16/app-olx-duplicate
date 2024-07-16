package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListMobilIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.Mobil.FormIklanMobilAksesori;
import com.example.beeli.ui.Iklan.FormIklan.Mobil.FormIklanMobilAudio;
import com.example.beeli.ui.Iklan.FormIklan.Mobil.FormIklanMobilBekas;
import com.example.beeli.ui.Iklan.FormIklan.Mobil.FormIklanMobilKomersial;
import com.example.beeli.ui.Iklan.FormIklan.Mobil.FormIklanMobilPart;
import com.example.beeli.ui.Iklan.FormIklan.Mobil.FormIklanMobilVelg;

public class MobilIklan extends AppCompatActivity {
    public static Activity subMobil;
    SubListMobilIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListMobilIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.mBekas, FormIklanMobilBekas.class);
        setOnClickListener(binding.mAksesori, FormIklanMobilAksesori.class);
        setOnClickListener(binding.mAudio, FormIklanMobilAudio.class);
        setOnClickListener(binding.mPart, FormIklanMobilPart.class);
        setOnClickListener(binding.mVelg, FormIklanMobilVelg.class);
        setOnClickListener(binding.mKomersial, FormIklanMobilKomersial.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(MobilIklan.this, cls);
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
