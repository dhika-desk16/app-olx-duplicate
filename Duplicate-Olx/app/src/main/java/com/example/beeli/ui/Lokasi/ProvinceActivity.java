package com.example.beeli.ui.Lokasi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.databinding.ActivityLocationBinding;
import com.example.beeli.model.DistrictModel;
import com.example.beeli.model.ProvinceModel;
import com.example.beeli.model.RegenciesModel;
import com.example.beeli.model.VillageModel;
import com.example.beeli.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinceActivity extends AppCompatActivity implements LocationAdapter.OnItemClickListener {
    private ActivityLocationBinding binding;
    private RecyclerView recyclerView;
    private LocationAdapter provinceAdapter;
    public static LocationProgressBar dialog;
    public static String Village;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.provinsi;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        provinceAdapter = new LocationAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(provinceAdapter);
        dialog = new LocationProgressBar(ProvinceActivity.this);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        lokasi(binding.testLoc1, binding.lokasi);
        getProvince();
        binding.tvclocation.setText(Village);
    }

    void lokasi(LinearLayout linear, TextView tv) {
        linear.setOnClickListener(v -> binding.tvclocation.setText(tv.getText()));
    }

    private void getProvince() {
        dialog.show();
        ApiService.endpoint().getProvince().enqueue(new Callback<List<ProvinceModel>>() {
            @Override
            public void onResponse(Call<List<ProvinceModel>> call, Response<List<ProvinceModel>> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    List<ProvinceModel> provinceList = response.body();
                    if (provinceList != null) {
                        provinceAdapter.setProvinces(provinceList);
                    }
                } else {
                    Toast.makeText(ProvinceActivity.this, "Gagal Mendapatkan Lokasi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProvinceModel>> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(ProvinceActivity.this, "Gagal Mendapat Lokasi", Toast.LENGTH_SHORT).show();
                Log.d("Lokasi", "onFailure: " + throwable);
            }
        });
    }

    private void saveProvince(String province) {
        SharedPreferences sharedPreferences = getSharedPreferences("LokasiUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Province", province);
        editor.apply();
        Toast.makeText(this,  province, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickProvince(ProvinceModel province) {
        saveProvince(province.getName());
        Intent intent = new Intent(this, RegenciesActivity.class);
        intent.putExtra("selected_province", province);
        startActivity(intent);
    }

    @Override
    public void onItemClickRegency(RegenciesModel regency) {
        // Not used in this activity
    }

    @Override
    public void onItemClickDistrict(DistrictModel district) {
        // Not used in this activity
    }

    @Override
    public void onItemClickVillage(VillageModel village) {
        // Not used in this activity
    }
}
