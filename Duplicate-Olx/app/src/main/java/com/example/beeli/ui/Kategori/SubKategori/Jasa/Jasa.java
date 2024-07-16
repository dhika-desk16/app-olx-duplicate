package com.example.beeli.ui.Kategori.SubKategori.Jasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListJasaBinding;
import com.example.beeli.ui.Home.Drawer;

public class Jasa extends AppCompatActivity {
    SubListJasaBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListJasaBinding.inflate(getLayoutInflater());
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
        clickSubKategori(binding.jJasa, "Jasa");
        clickSubKategori(binding.jKerja, "Cari Pekerjaan");
        clickSubKategori(binding.jLowongan, "Lowongan");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jasa.this, Drawer.class);
                intent.putExtra("FragmentTag", "JasaFragment");
                intent.putExtra("subKategori", selectedSubKategori);
//                LocalBroadcastManager.getInstance()
                finish();
            }
        });
    }
}