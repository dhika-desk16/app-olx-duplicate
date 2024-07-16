package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.databinding.SubListPropertiIklanBinding;
import com.example.beeli.ui.Iklan.FormIklan.Properti.FormIklanPropertiDijualBK;
import com.example.beeli.ui.Iklan.FormIklan.Properti.FormIklanPropertiDijualRA;
import com.example.beeli.ui.Iklan.FormIklan.Properti.FormIklanPropertiDisewakanBK;
import com.example.beeli.ui.Iklan.FormIklan.Properti.FormIklanPropertiDisewakanRA;
import com.example.beeli.ui.Iklan.FormIklan.Properti.FormIklanPropertiIndekos;
import com.example.beeli.ui.Iklan.FormIklan.Properti.FormIklanPropertiTanah;

public class PropertiIklan extends AppCompatActivity {
    public static Activity subProperti;
    SubListPropertiIklanBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= SubListPropertiIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.pJualRumah, FormIklanPropertiDijualRA.class);
        setOnClickListener(binding.pSewaRumah, FormIklanPropertiDisewakanRA.class);
        setOnClickListener(binding.pTanah, FormIklanPropertiTanah.class);
        setOnClickListener(binding.pKos, FormIklanPropertiIndekos.class);
        setOnClickListener(binding.pJualBangunan, FormIklanPropertiDijualBK.class);
        setOnClickListener(binding.pSewaBangunan, FormIklanPropertiDisewakanBK.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(PropertiIklan.this, cls);
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
