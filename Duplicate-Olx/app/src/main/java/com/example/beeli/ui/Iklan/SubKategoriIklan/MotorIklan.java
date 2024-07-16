package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.ui.Iklan.FormIklan.Motor.FormIklanMotorAksesori;
import com.example.beeli.ui.Iklan.FormIklan.Motor.FormIklanMotorBekas;
import com.example.beeli.ui.Iklan.FormIklan.Motor.FormIklanMotorHelm;
import com.example.beeli.ui.Iklan.FormIklan.Motor.FormIklanMotorPart;
import com.example.beeli.databinding.SubListMotorIklanBinding;

public class MotorIklan extends AppCompatActivity {
    public static Activity subMotor;
    SubListMotorIklanBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListMotorIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.mrBekas, FormIklanMotorBekas.class);
        setOnClickListener(binding.mrAksesori, FormIklanMotorAksesori.class);
        setOnClickListener(binding.mrHelm, FormIklanMotorHelm.class);
        setOnClickListener(binding.mrPart, FormIklanMotorPart.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(MotorIklan.this, cls);
            startActivity(intent);
        });
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
