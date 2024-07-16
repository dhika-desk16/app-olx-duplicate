package com.example.beeli.ui.Kategori.SubKategori.Anak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListAnakBinding;
import com.example.beeli.ui.Home.Drawer;

public class Anak extends AppCompatActivity {
    SubListAnakBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListAnakBinding.inflate(getLayoutInflater());
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

        clickSubKategori(binding.aAll, "Semua");
        clickSubKategori(binding.aToy, "Boneka & Mainan Anak");
        clickSubKategori(binding.aBuku, "Buku Anak");
        clickSubKategori(binding.aOthers, "Lain-Lain");
        clickSubKategori(binding.aPakaian, "Pakaian");
        clickSubKategori(binding.apBayi, "Perlengkapan Bayi");
        clickSubKategori(binding.apIbu, "Perlengkapan Ibu Bayi");
        clickSubKategori(binding.aStroller, "Stroller");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Anak.this, Drawer.class);
                intent.putExtra("FragmentTag", "AnakFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}
