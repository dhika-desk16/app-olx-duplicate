package com.example.beeli.ui.Kategori.SubKategori.Pribadi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListPribadiBinding;
import com.example.beeli.ui.Home.Drawer;

public class Pribadi extends AppCompatActivity {
    SubListPribadiBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListPribadiBinding.inflate(getLayoutInflater());
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

        clickSubKategori(binding.prAll, "Semua");
        clickSubKategori(binding.prcOlahraga, "Pakaian Olahraga");
        clickSubKategori(binding.prHias, "Perhiasan");
        clickSubKategori(binding.prhJam, "Jam Tangan");
        clickSubKategori(binding.prNutrisi, "Nutrisi & Suplemen");
        clickSubKategori(binding.prObat, "Terapi & Pengobatan");
        clickSubKategori(binding.prOthers, "Lainnya");
        clickSubKategori(binding.prParfum, "Makeup & Parfum");
        clickSubKategori(binding.prpFashion, "Fashion Pria");
        clickSubKategori(binding.prRawat, "Perawatan");
        clickSubKategori(binding.prwFashion, "Fashion Wanita");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pribadi.this, Drawer.class);
                intent.putExtra("FragmentTag", "PribadiFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}
