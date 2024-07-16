package com.example.beeli.ui.Kategori.SubKategori.Elektronik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListElektroBinding;
import com.example.beeli.ui.Home.Drawer;

public class Elektronik extends AppCompatActivity {
    SubListElektroBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListElektroBinding.inflate(getLayoutInflater());
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

        clickSubKategori(binding.eAll, "Semua");
        clickSubKategori(binding.eAksesoris, "Aksesoris HP & Tablet");
        clickSubKategori(binding.eGames, "Games & Console");
        clickSubKategori(binding.eFoto, "Fotografi");
        clickSubKategori(binding.eLampu, "Lampu");
        clickSubKategori(binding.eKomputer, "Komputer");
        clickSubKategori(binding.ePhone, "Handphone");
        clickSubKategori(binding.eTablet, "Tablet");
        clickSubKategori(binding.eTV, "TV & Audio, Video");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Elektronik.this, Drawer.class);
                intent.putExtra("FragmentTag", "ElektronikFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}
