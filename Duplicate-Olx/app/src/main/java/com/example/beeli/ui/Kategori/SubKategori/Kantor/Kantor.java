package com.example.beeli.ui.Kategori.SubKategori.Kantor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListKantorBinding;
import com.example.beeli.ui.Home.Drawer;

public class Kantor extends AppCompatActivity {
    SubListKantorBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListKantorBinding.inflate(getLayoutInflater());
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

        clickSubKategori(binding.kaAll, "Semua");
        clickSubKategori(binding.kaKantor, "Peralatan Kantor");
        clickSubKategori(binding.kaMesin, "Mesin & Keperluan Usaha");
        clickSubKategori(binding.kaStationery, "Stationary");
        clickSubKategori(binding.kaUsaha, "Peralatan Usaha");
        clickSubKategori(binding.kaOthers, "Lain-Lain");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kantor.this, Drawer.class);
                intent.putExtra("FragmentTag", "KantorFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}