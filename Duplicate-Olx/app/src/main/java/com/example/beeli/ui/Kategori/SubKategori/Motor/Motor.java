package com.example.beeli.ui.Kategori.SubKategori.Motor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListMotorBinding;
import com.example.beeli.ui.Home.Drawer;

public class Motor extends AppCompatActivity {
    SubListMotorBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListMotorBinding.inflate(getLayoutInflater());
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
        clickSubKategori(binding.mrAksesori, "Aksesoris");
        clickSubKategori(binding.mrBekas, "Motor Bekas");
        clickSubKategori(binding.mrHelm, "Helm");
        clickSubKategori(binding.mrPart, "Spare Part");
    }

    private void clickSubKategori(LinearLayout linear, String selectedSubKategori){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Motor.this, Drawer.class);
                intent.putExtra("FragmentTag", "MotorFragment");
                intent.putExtra("subKategori", selectedSubKategori);
                startActivity(intent);
                finish();
            }
        });
    }
}
