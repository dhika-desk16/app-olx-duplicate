package com.example.beeli.ui.Kategori.SubKategori.Properti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListPropertiBinding;
import com.example.beeli.ui.Home.Drawer;

public class Properti extends AppCompatActivity {
    SubListPropertiBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListPropertiBinding.inflate(getLayoutInflater());
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
        clickSubKategori(binding.pJualBangunan, "Dijual Bangunan Komersil");
        clickSubKategori(binding.pJualRumah, "Dijual Rumah Dan Apartemen");
        clickSubKategori(binding.pSewaBangunan, "Disewakan Bangunan Komersil");
        clickSubKategori(binding.pSewaRumah, "Disewakan Rumah Dan Apartemen");
        clickSubKategori(binding.pTanah, "Tanah");
        clickSubKategori(binding.pKos, "Indekos");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Properti.this, Drawer.class);
                intent.putExtra("FragmentTag", "PropertiFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}
