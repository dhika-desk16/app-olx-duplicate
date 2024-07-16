package com.example.beeli.ui.Lokasi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.databinding.PageVillageBinding;
import com.example.beeli.model.DistrictModel;
import com.example.beeli.model.ProvinceModel;
import com.example.beeli.model.RegenciesModel;
import com.example.beeli.model.VillageModel;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Login.FormLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VillageActivity extends AppCompatActivity implements LocationAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private PageVillageBinding binding;
    private LocationAdapter villageAdapter;
    public static String Village;
    private DistrictModel selectedDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PageVillageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView selectedRegencyView = binding.selectedRegency;

        selectedDistrict = (DistrictModel) getIntent().getSerializableExtra("selected_district");
        if (selectedDistrict != null) {
            selectedRegencyView.setText("Kecamatan: " + selectedDistrict.getName());
        }

        recyclerView = binding.kelurahan;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        villageAdapter = new LocationAdapter(new ArrayList<>(), this, false, false, false);

        recyclerView.setAdapter(villageAdapter);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getVillage();
    }

    private void getVillage() {
        ApiService.endpoint().getVillage(selectedDistrict.getId()).enqueue(new Callback<List<VillageModel>>() {
            @Override
            public void onResponse(Call<List<VillageModel>> call, Response<List<VillageModel>> response) {
                if (response.isSuccessful()) {
                    List<VillageModel> villageList = response.body();
                    if (villageList != null) {
                        villageAdapter.setVillage(villageList);
                    }
                }else{
                    Log.d("Bad Ending", "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<VillageModel>> call, Throwable throwable) {
                Log.d("Bad Ending", "onFailure: " + throwable);
                Toast.makeText(VillageActivity.this, "Gagal Mendapatkan Lokasi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveVillage(String village){
        SharedPreferences sharedPreferences = getSharedPreferences("LokasiUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Village", village);
        editor.apply();
        Toast.makeText(this, village, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickProvince(ProvinceModel province) {
        // Not applicable in this activity
    }

    @Override
    public void onItemClickRegency(RegenciesModel regency) {
        // Not applicable in this activity
    }

    @Override
    public void onItemClickDistrict(DistrictModel district) {
        // Not applicable in this activity
    }

    @Override
    public void onItemClickVillage(VillageModel village) {
        saveVillage(village.getName());
        Village = village.getName();
        Intent intent = new Intent(VillageActivity.this, FormLogin.class);
        intent.putExtra("selected_village", village);
        startActivity(intent);
    }
}
