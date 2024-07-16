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

import com.example.beeli.databinding.PageDistrictBinding;
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

public class DistrictActivity extends AppCompatActivity implements LocationAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    PageDistrictBinding binding;
    private LocationAdapter districtAdapter;
    public static String District;
    private RegenciesModel selectedRegency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PageDistrictBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView selectedRegencyView = binding.selectedRegency;

        selectedRegency = (RegenciesModel) getIntent().getSerializableExtra("selected_regency");
        if (selectedRegency != null) {
            selectedRegencyView.setText("Kota / Kabupaten : " + selectedRegency.getName());
        }

        recyclerView = binding.district;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        districtAdapter = new LocationAdapter(new ArrayList<>(), this, false, false);

        recyclerView.setAdapter(districtAdapter);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDistricts();
    }

    private void getDistricts() {
        ApiService.endpoint().getDistricts(String.valueOf(selectedRegency.getId())).enqueue(new Callback<List<DistrictModel>>() {
            @Override
            public void onResponse(Call<List<DistrictModel>> call, Response<List<DistrictModel>> response) {
                if (response.isSuccessful()) {
                    List<DistrictModel> districtList = response.body();
                    if (districtList != null) {
                        districtAdapter.setDistricts(districtList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DistrictModel>> call, Throwable throwable) {
                Toast.makeText(DistrictActivity.this, "Gagal Mendapatkan Lokasi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDistrict(String district){
        SharedPreferences sharedPreferences = getSharedPreferences("LokasiUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("District", district);
        editor.apply();
        Toast.makeText(this, district, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickProvince(ProvinceModel province) {

    }

    @Override
    public void onItemClickRegency(RegenciesModel regency) {

    }

    @Override
    public void onItemClickDistrict(DistrictModel district) {
        saveDistrict(district.getName());
        District = district.getName();
        Intent intent = new Intent(DistrictActivity.this, VillageActivity.class);
        intent.putExtra("selected_district", district);
        startActivity(intent);
    }

    @Override
    public void onItemClickVillage(VillageModel village) {

    }

}