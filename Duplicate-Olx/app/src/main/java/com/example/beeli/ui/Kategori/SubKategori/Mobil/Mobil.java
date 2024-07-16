package com.example.beeli.ui.Kategori.SubKategori.Mobil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListMobilBinding;
import com.example.beeli.ui.Home.Drawer;

public class Mobil extends AppCompatActivity {
    SubListMobilBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListMobilBinding.inflate(getLayoutInflater());
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
        clickSubKategori(binding.mAksesori, "Aksesoris");
        clickSubKategori(binding.mAudio, "Audio");
        clickSubKategori(binding.mBan, "Velg Dan Ban");
        clickSubKategori(binding.mBekas, "Mobil Bekas");
        clickSubKategori(binding.mPart, "Spare Part");
        clickSubKategori(binding.mTruk, "Truk Dan Kendaraan Komersial");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobil.this, Drawer.class);
                intent.putExtra("FragmentTag", "MobilFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}