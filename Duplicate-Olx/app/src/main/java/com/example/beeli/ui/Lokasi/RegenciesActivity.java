package com.example.beeli.ui.Lokasi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.databinding.PageRegenciesBinding;
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

public class RegenciesActivity extends AppCompatActivity implements LocationAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    PageRegenciesBinding binding;
    private LocationAdapter regencyAdapter;
    private ProvinceModel selectedProvince;
    public static String Regencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PageRegenciesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView selectedProvinceView = binding.selectedProvince;

        selectedProvince = (ProvinceModel) getIntent().getSerializableExtra("selected_province");
        String provinceName = getIntent().getStringExtra("province_name");
        if (selectedProvince != null) {
            selectedProvinceView.setText("Provinsi : " + selectedProvince.getName());
        }

        recyclerView = binding.KotaKab;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        regencyAdapter = new LocationAdapter(new ArrayList<>(), this, false);
        recyclerView.setAdapter(regencyAdapter);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getRegencies();
    }

    private void getRegencies() {
        ApiService.endpoint().getRegencies(String.valueOf(selectedProvince.getId())).enqueue(new Callback<List<RegenciesModel>>() {
            @Override
            public void onResponse(Call<List<RegenciesModel>> call, Response<List<RegenciesModel>> response) {
                if (response.isSuccessful()) {
                    List<RegenciesModel> regenciesList = response.body();
                    if (regenciesList != null) {
                        regencyAdapter.setRegencies(regenciesList);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RegenciesModel>> call, Throwable throwable) {
                Toast.makeText(RegenciesActivity.this, "Gagal Mendapatkan Lokasi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveRegencies(String regencies){
        SharedPreferences sharedPreferences = getSharedPreferences("LokasiUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Regencies", regencies);
        editor.apply();
        Toast.makeText(this, regencies, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickProvince(ProvinceModel province) {
        // Not used in this activity
    }

    @Override
    public void onItemClickRegency(RegenciesModel regency) {
        saveRegencies(regency.getName());
        Regencies = regency.getName();
        Intent intent = new Intent(this, DistrictActivity.class);
        intent.putExtra("selected_regency", regency);
        startActivity(intent);
    }
    @Override
    public void onItemClickDistrict(DistrictModel district) {
    }

    @Override
    public void onItemClickVillage(VillageModel village) {

    }
}