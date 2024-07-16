package com.example.beeli.ui.Iklan.SubKategoriIklan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiFashionPria;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiFashionWanita;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiJamTangan;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiLainnya;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiMakeUp;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiPakaianOlga;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiPerawatan;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiPerhiasan;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiSuplemen;
import com.example.beeli.ui.Iklan.FormIklan.Pribadi.FormIklanPribadiTerapiPengobatan;
import com.example.beeli.databinding.SubListPribadiIklanBinding;

public class PribadiIklan extends AppCompatActivity {
    public static Activity subPribadi;
    SubListPribadiIklanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SubListPribadiIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        setOnClickListener(binding.prwFashion, FormIklanPribadiFashionWanita.class);
        setOnClickListener(binding.prpFashion, FormIklanPribadiFashionPria.class);
        setOnClickListener(binding.prhJam, FormIklanPribadiJamTangan.class);
        setOnClickListener(binding.prcOlahraga, FormIklanPribadiPakaianOlga.class);
        setOnClickListener(binding.prHias, FormIklanPribadiPerhiasan.class);
        setOnClickListener(binding.prParfum, FormIklanPribadiMakeUp.class);
        setOnClickListener(binding.prObat, FormIklanPribadiTerapiPengobatan.class);
        setOnClickListener(binding.prRawat, FormIklanPribadiPerawatan.class);
        setOnClickListener(binding.prNutrisi, FormIklanPribadiSuplemen.class);
        setOnClickListener(binding.prOthers, FormIklanPribadiLainnya.class);
    }

    private void setOnClickListener(View view, Class<?> cls) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(PribadiIklan.this, cls);
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
