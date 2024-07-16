package com.example.beeli.ui.IklanSayaFavorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.adapter.IklanFavoriteAdapter;
import com.example.beeli.adapter.IklanSayaAdapter;
import com.example.beeli.databinding.ActivityMyIklanBinding;
import com.example.beeli.databinding.ActivityMyIklanFavBinding;
import com.example.beeli.model.IklanRekomendasiFavoriteModel;
import com.example.beeli.model.IklanSayaModel;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Barang.ViewBarangFragment;
import com.example.beeli.ui.Home.Drawer;
import com.example.beeli.ui.Iklan.PasangIklan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IklanSayaFavorite extends AppCompatActivity implements IklanSayaAdapter.OnItemClickListener, IklanFavoriteAdapter.OnItemClickListener {
    ActivityMyIklanBinding binding;
    ActivityMyIklanFavBinding favBinding;
    private Context context;
    private List<Map<String, IklanSayaModel.IklanSaya.Iklan>> iklanSayaList = new ArrayList<>();
    private List<IklanRekomendasiFavoriteModel.IklanFavorit> favoriteList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favBinding = ActivityMyIklanFavBinding.inflate(getLayoutInflater());
        binding= ActivityMyIklanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iklan();
    }

    private void iklan(){
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IklanSayaAdapter adapter = new IklanSayaAdapter(context, iklanSayaList);
        RecyclerView recyclerView = findViewById(R.id.myIklanContent);
        recyclerView.setLayoutManager(new GridLayoutManager( this, 2)); // 2 columns in each row
        recyclerView.setAdapter(adapter);

        getIklanSaya(adapter);

        binding.pasIklan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IklanSayaFavorite.this, PasangIklan.class);
                startActivity(intent);
                finish();
            }
        });
        binding.favPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(favBinding.getRoot());
                fav();
            }
        });
    }

    private void fav(){
        Toolbar toolbar = favBinding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IklanFavoriteAdapter adapter = new IklanFavoriteAdapter(context, favoriteList);
        RecyclerView recyclerView = findViewById(R.id.myFavContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getFavorites(adapter);

        favBinding.iklanPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(binding.getRoot());
                iklan();
            }
        });
        favBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IklanSayaFavorite.this, Drawer.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    func

    @Override
    public void onItemClick(IklanSayaModel.IklanSaya.Iklan data) {
        openFragment(data);
    }

    private void getIklanSaya(IklanSayaAdapter adapter){
        Helper.progressDialog(this);
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).getIklanSaya().enqueue(new Callback<IklanSayaModel>() {
                    @Override
                    public void onResponse(Call<IklanSayaModel> call, Response<IklanSayaModel> response) {
                        Helper.disableDialog();
                        if (response.isSuccessful()){
                            IklanSayaModel model = response.body();
                            iklanSayaList.clear();

                            List<List<Map<String, IklanSayaModel.IklanSaya.Iklan>>> allIklan = model.getData_iklan().getAllIklanLists();
                            for (List<Map<String, IklanSayaModel.IklanSaya.Iklan>> iklanList : allIklan){
                                if (iklanList != null){
                                    iklanSayaList.addAll(iklanList);
                                }
                            }

                            if (iklanSayaList.isEmpty()){
                                binding.iklanKosong.setVisibility(View.VISIBLE);
                            } else {
                                binding.iklanKosong.setVisibility(View.GONE);
                                binding.myIklanContent.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<IklanSayaModel> call, Throwable throwable) {
                        Helper.disableDialog();
                        Log.d("Ending", "onResponse: "+ throwable);
                    }
                });
            }

            @Override
            public void onTokenError() {
                Helper.disableDialog();
                Toast.makeText(IklanSayaFavorite.this, "Silahkan Login terlebih dahulu !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFavorites(IklanFavoriteAdapter adapter){
        Helper.progressDialog(this);
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).getIklanFavorites().enqueue(new Callback<IklanRekomendasiFavoriteModel>() {
                    @Override
                    public void onResponse(Call<IklanRekomendasiFavoriteModel> call, Response<IklanRekomendasiFavoriteModel> response) {
                        Helper.disableDialog();
                        if (response.isSuccessful()){
                            IklanRekomendasiFavoriteModel model = response.body();
                            Log.e("FavModel", "onResponse: "+ model );
                            favoriteList.clear();
                            favoriteList.addAll(model.getData());

                            if (favoriteList.isEmpty()){
                                favBinding.favKosong.setVisibility(View.VISIBLE);
                            } else {
                                favBinding.favKosong.setVisibility(View.GONE);
                                favBinding.myFavContent.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<IklanRekomendasiFavoriteModel> call, Throwable throwable) {
                        Helper.disableDialog();
                        Log.e("hwello", "onFailure: "+ throwable );
                        Toast.makeText(IklanSayaFavorite.this, ""+ throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onTokenError() {
                Helper.disableDialog();
                Toast.makeText(IklanSayaFavorite.this, "Silahkan Login terlebih dahulu !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(IklanRekomendasiFavoriteModel.IklanFavorit data) {
        openFragmentFav(data);
    }

    private void openFragmentFav(IklanRekomendasiFavoriteModel.IklanFavorit data){
        ViewBarangFragment viewBarangFragment = new ViewBarangFragment();

        String[] gambarArray = new String[]{
                data.getDataGambar().getGambar1(),
                data.getDataGambar().getGambar2(),
                data.getDataGambar().getGambar3(),
                data.getDataGambar().getGambar4(),
                data.getDataGambar().getGambar5(),
                data.getDataGambar().getGambar6(),
                data.getDataGambar().getGambar7(),
                data.getDataGambar().getGambar8(),
                data.getDataGambar().getGambar9(),
                data.getDataGambar().getGambar10(),
                data.getDataGambar().getGambar11(),
                data.getDataGambar().getGambar12()
        };

        Bundle bundle = new Bundle();
        bundle.putString("judul_iklan", data.getDataIklan().getJudul_iklan());
        bundle.putString("kodeIklan", data.getIdentityIklan().getKode_iklan());
        bundle.putString("name", data.getDataIklan().getName());
        bundle.putString("deskripsi", data.getDataIklan().getDeskripsi());
        bundle.putInt("harga", data.getDataIklan().getHarga());
        bundle.putString("kondisi", "Kondisi : "+ data.getDataIklan().getKondisi());
        bundle.putStringArray("gambarArray", gambarArray);

        bundle.putString("merk", data.getDataIklan().getMerk());
        bundle.putString("tipe", data.getDataIklan().getTipe());
        bundle.putString("gaji_dari", data.getDataIklan().getGaji_dari());
        bundle.putString("gaji_sampai", data.getDataIklan().getGaji_sampai());
        bundle.putString("bahan_bakar", data.getDataIklan().getTipe_bahan_bakar());
        bundle.putString("tahun", data.getDataIklan().getTahun());
        bundle.putString("warna", data.getDataIklan().getWarna());
        bundle.putString("luas_bangunan", data.getDataIklan().getLuas_bangunan());
        bundle.putString("luas_tanah", data.getDataIklan().getLuas_tanah());
        bundle.putString("kamar_tidur", data.getDataIklan().getKamar_tidur());
        bundle.putString("kamar_mandi", data.getDataIklan().getKamar_mandi());
        bundle.putString("lantai", data.getDataIklan().getLantai());
        bundle.putString("fasilitas", data.getDataIklan().getFasilitas());
        bundle.putString("alamat_lokasi", data.getDataIklan().getAlamat_lokasi());
        bundle.putString("sertifikasi", data.getDataIklan().getSertifikasi());

        viewBarangFragment.setArguments(bundle);

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, viewBarangFragment, "ViewBarangFragment");
        transaction.commit();
    }
    private void openFragment(IklanSayaModel.IklanSaya.Iklan data){
        ViewBarangFragment viewBarangFragment = new ViewBarangFragment();

        String[] gambarArray = new String[]{
                data.getDataGambar().getGambar1(),
                data.getDataGambar().getGambar2(),
                data.getDataGambar().getGambar3(),
                data.getDataGambar().getGambar4(),
                data.getDataGambar().getGambar5(),
                data.getDataGambar().getGambar6(),
                data.getDataGambar().getGambar7(),
                data.getDataGambar().getGambar8(),
                data.getDataGambar().getGambar9(),
                data.getDataGambar().getGambar10(),
                data.getDataGambar().getGambar11(),
                data.getDataGambar().getGambar12()
        };

        Bundle bundle = new Bundle();
        bundle.putString("judul_iklan", data.getDataIklan().getJudul_iklan());
        bundle.putString("kodeIklan", data.getIdentityIklan().getKode_iklan());
        bundle.putString("name", data.getDataIklan().getName());
        bundle.putString("deskripsi", data.getDataIklan().getDeskripsi());
        bundle.putInt("harga", data.getDataIklan().getHarga());
        bundle.putString("kondisi", "Kondisi : "+ data.getDataIklan().getKondisi());
        bundle.putStringArray("gambarArray", gambarArray);

        bundle.putString("merk", data.getDataIklan().getMerk());
        bundle.putString("tipe", data.getDataIklan().getTipe());
        bundle.putString("gaji_dari", data.getDataIklan().getGaji_dari());
        bundle.putString("gaji_sampai", data.getDataIklan().getGaji_sampai());
        bundle.putString("bahan_bakar", data.getDataIklan().getTipe_bahan_bakar());
        bundle.putString("tahun", data.getDataIklan().getTahun());
        bundle.putString("warna", data.getDataIklan().getWarna());
        bundle.putString("luas_bangunan", data.getDataIklan().getLuas_bangunan());
        bundle.putString("luas_tanah", data.getDataIklan().getLuas_tanah());
        bundle.putString("kamar_tidur", data.getDataIklan().getKamar_tidur());
        bundle.putString("kamar_mandi", data.getDataIklan().getKamar_mandi());
        bundle.putString("lantai", data.getDataIklan().getLantai());
        bundle.putString("fasilitas", data.getDataIklan().getFasilitas());
        bundle.putString("alamat_lokasi", data.getDataIklan().getAlamat_lokasi());
        bundle.putString("sertifikasi", data.getDataIklan().getSertifikasi());

        viewBarangFragment.setArguments(bundle);

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, viewBarangFragment, "ViewBarangFragment");
        transaction.commit();
    }


}
