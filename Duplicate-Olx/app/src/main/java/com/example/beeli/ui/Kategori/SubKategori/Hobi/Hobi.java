package com.example.beeli.ui.Kategori.SubKategori.Hobi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListOlahragaBinding;
import com.example.beeli.ui.Home.Drawer;

public class Hobi extends AppCompatActivity {
    SubListOlahragaBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListOlahragaBinding.inflate(getLayoutInflater());
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

        clickSubKategori(binding.oAll, "Semua");
        clickSubKategori(binding.oOlahraga, "Olahraga");
        clickSubKategori(binding.oAntik, "Barang Antik");
        clickSubKategori(binding.oBuku, "Buku & Majalah");
        clickSubKategori(binding.oCrafts, "Handicraft");
        clickSubKategori(binding.oFilm, "Musik & Film");
        clickSubKategori(binding.oKoleksi, "Koleksi");
        clickSubKategori(binding.oMusic, "Alat-alat Musik");
        clickSubKategori(binding.oHewan, "Hewan Peliharaan");
        clickSubKategori(binding.oSepeda, "Sepeda & Aksesoris");
        clickSubKategori(binding.oToy, "Mainan Hobi");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hobi.this, Drawer.class);
                intent.putExtra("FragmentTag", "HobiFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}