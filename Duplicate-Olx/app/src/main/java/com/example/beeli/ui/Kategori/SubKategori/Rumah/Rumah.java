package com.example.beeli.ui.Kategori.SubKategori.Rumah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListRumahBinding;
import com.example.beeli.ui.Home.Drawer;

public class Rumah extends AppCompatActivity {
    SubListRumahBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListRumahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        view();
    }


    private void view(){
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clickSubKategori(binding.mAll, "Semua");
        clickSubKategori(binding.rDekor, "Dekorasi Rumah");
        clickSubKategori(binding.rJam, "Jam");
        clickSubKategori(binding.rLampu, "Lampu");
        clickSubKategori(binding.rMakanan, "Makanan & Minuman");
        clickSubKategori(binding.rMebel, "Mebel");
        clickSubKategori(binding.rPerlengkapan, "Perlengkapan Rumah Tangga");
        clickSubKategori(binding.rTaman, "Konstruksi Dan Taman");
        clickSubKategori(binding.rOthers, "Lain-Lain");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rumah.this, Drawer.class);
                intent.putExtra("FragmentTag", "RumahFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}
