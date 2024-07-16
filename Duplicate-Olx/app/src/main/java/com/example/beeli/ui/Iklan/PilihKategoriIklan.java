package com.example.beeli.ui.Iklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.ListKategoriIklanBinding;
import com.example.beeli.ui.Iklan.SubKategoriIklan.AnakIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.ElektronikIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.JasaIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.KantorIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.MobilIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.MotorIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.OlahragaIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.PribadiIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.PropertiIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.RumahIklan;

public class PilihKategoriIklan extends AppCompatActivity {
    ListKategoriIklanBinding binding;
    public static Activity menuKategoriIklan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ListKategoriIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onClick(binding.kAnak, AnakIklan.class);
        onClick(binding.kElektro, ElektronikIklan.class);
        onClick(binding.kJasa, JasaIklan.class);
        onClick(binding.kKantor, KantorIklan.class);
        onClick(binding.kMobil, MobilIklan.class);
        onClick(binding.kMotor, MotorIklan.class);
        onClick(binding.kOlahraga, OlahragaIklan.class);
        onClick(binding.kPribadi, PribadiIklan.class);
        onClick(binding.kProperti, PropertiIklan.class);
        onClick(binding.kRumah, RumahIklan.class);

    }

    private void onClick(LinearLayout linear, final Class<?> cls){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihKategoriIklan.this, cls);
                startActivity(intent);
            }
        });
    }

}