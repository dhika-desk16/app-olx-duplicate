package com.example.beeli.ui.Kategori;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.ListKategoriBinding;
import com.example.beeli.ui.Kategori.SubKategori.Anak.Anak;
import com.example.beeli.ui.Kategori.SubKategori.Elektronik.Elektronik;
import com.example.beeli.ui.Kategori.SubKategori.Jasa.Jasa;
import com.example.beeli.ui.Kategori.SubKategori.Kantor.Kantor;
import com.example.beeli.ui.Kategori.SubKategori.Mobil.Mobil;
import com.example.beeli.ui.Kategori.SubKategori.Motor.Motor;
import com.example.beeli.ui.Kategori.SubKategori.Hobi.Hobi;
import com.example.beeli.ui.Kategori.SubKategori.Pribadi.Pribadi;
import com.example.beeli.ui.Kategori.SubKategori.Properti.Properti;
import com.example.beeli.ui.Kategori.SubKategori.Rumah.Rumah;

public class Kategori extends AppCompatActivity {
    ListKategoriBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ListKategoriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onClickKategori(binding.kMobil, Mobil.class);
        onClickKategori(binding.kProperti, Properti.class);
        onClickKategori(binding.kMotor, Motor.class);
        onClickKategori(binding.kJasa, Jasa.class);
        onClickKategori(binding.kElektro, Elektronik.class);
        onClickKategori(binding.kOlahraga, Hobi.class);
        onClickKategori(binding.kRumah, Rumah.class);
        onClickKategori(binding.kPribadi, Pribadi.class);
        onClickKategori(binding.kAnak, Anak.class);
        onClickKategori(binding.kKantor, Kantor.class);
    }

    private void onClickKategori(LinearLayout kategori, final Class<?> destinationClass) {
        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kategori.this, destinationClass);
                startActivity(intent);
            }
        });
    }
}
