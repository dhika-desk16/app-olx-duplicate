package com.example.beeli.ui.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.beeli.R;
import com.example.beeli.adapter.HomeAdapter;
import com.example.beeli.databinding.FragmentBerandaBinding;
import com.example.beeli.model.IklanRekomendasiFavoriteModel;
import com.example.beeli.model.ProfileModel;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Barang.ViewBarangFragment;
import com.example.beeli.ui.Kategori.Kategori;
import com.example.beeli.ui.Kategori.SubKategori.Anak.Anak;
import com.example.beeli.ui.Kategori.SubKategori.Elektronik.Elektronik;
import com.example.beeli.ui.Kategori.SubKategori.Hobi.Hobi;
import com.example.beeli.ui.Kategori.SubKategori.Jasa.Jasa;
import com.example.beeli.ui.Kategori.SubKategori.Kantor.Kantor;
import com.example.beeli.ui.Kategori.SubKategori.Mobil.Mobil;
import com.example.beeli.ui.Kategori.SubKategori.Motor.Motor;
import com.example.beeli.ui.Kategori.SubKategori.Pribadi.Pribadi;
import com.example.beeli.ui.Kategori.SubKategori.Properti.Properti;
import com.example.beeli.ui.Kategori.SubKategori.Rumah.Rumah;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener{
    private List<IklanRekomendasiFavoriteModel.IklanFavorit> listData = new ArrayList<>();
    private final boolean[] draw = {false};
    FragmentBerandaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBerandaBinding.inflate(inflater, container, false);
        view();

        return binding.getRoot();
    }

    public void view() {
        getAlamat();
        binding.bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(drawer.findViewById(R.id.nav_drawer));
            }
        });
        binding.sKategori.setOnClickListener(v -> startActivity(new Intent(getActivity(), Kategori.class)));

        HomeAdapter adapter = new HomeAdapter(getContext(), listData);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

//        get Rekomendasi Iklan
        rekomendasi(adapter);

        onClickKategoriHorizon(binding.hikMobil, Mobil.class);
        onClickKategoriHorizon(binding.hikProperti, Properti.class);
        onClickKategoriHorizon(binding.hikMotor, Motor.class);
        onClickKategoriHorizon(binding.hikJasa, Jasa.class);
        onClickKategoriHorizon(binding.hikElektero, Elektronik.class);
        onClickKategoriHorizon(binding.hikOlahraga, Hobi.class);
        onClickKategoriHorizon(binding.hikRumah, Rumah.class);
        onClickKategoriHorizon(binding.hikPribadi, Pribadi.class);
        onClickKategoriHorizon(binding.hikAnak, Anak.class);
        onClickKategoriHorizon(binding.hikKantor, Kantor.class);

        onClickKategoriFoot(binding.mnKategori, binding.kDraw, binding.liKategori);
        onClickKategoriFoot(binding.ktMobil, binding.mDraw, binding.iMobil);
        onClickKategoriFoot(binding.ktProperti, binding.pDraw, binding.iProperti);
        onClickKategoriFoot(binding.ktMotor, binding.mrDraw, binding.iMotor);
        onClickKategoriFoot(binding.ktJasa, binding.jDraw, binding.iJasa);
        onClickKategoriFoot(binding.ktElektro, binding.eDraw, binding.iElektro);
        onClickKategoriFoot(binding.ktOlahraga, binding.oDraw, binding.iOlahraga);
        onClickKategoriFoot(binding.ktRumah, binding.rDraw, binding.iRumah);
        onClickKategoriFoot(binding.ktPribadi, binding.prDraw, binding.iPribadi);
        onClickKategoriFoot(binding.ktAnak, binding.aDraw, binding.iAnak);
        onClickKategoriFoot(binding.ktKantor, binding.kaDraw, binding.iKantor);
    }

    private void onClickKategoriFoot(LinearLayout kategori, ImageView dDraw, LinearLayout item) {
        kategori.setOnClickListener(v -> {
            if (draw[0]) {
                dDraw.setImageResource(R.drawable.arrow_up);
                item.setVisibility(View.VISIBLE);
                draw[0] = false;
            } else {
                dDraw.setImageResource(R.drawable.arrow_down);
                item.setVisibility(View.GONE);
                draw[0] = true;
            }
            binding.aDraw.invalidate();
        });
    }
    private void onClickKategoriHorizon(LinearLayout linear, final Class<?> cls){
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), cls);
                startActivity(intent);
            }
        });
    }

//    Req Method
private void getAlamat(){
    ApiService.getToken(new ApiService.TokenCallback() {
        @Override
        public void onTokenReceived(String token) {
            ApiService.endpoint(token).getProfile().enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ProfileModel profileModel = response.body();
                        ProfileModel.User user = profileModel.getData();
                        binding.tvLocation.setText(user.getAlamat());

                    } else {
                        Log.d("getUserProfile", "User listData not found");
                    }
                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable throwable) {
                    Log.d("getUserProfile", "Failed to fetch user ta" + throwable.getMessage());
                }
            });
        }

        @Override
        public void onTokenError() {

        }
    });
}
    private void rekomendasi(HomeAdapter adapter) {
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).getRekomendasi().enqueue(new Callback<IklanRekomendasiFavoriteModel>() {
                    @Override
                    public void onResponse(Call<IklanRekomendasiFavoriteModel> call, Response<IklanRekomendasiFavoriteModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            IklanRekomendasiFavoriteModel model = response.body();

                            listData.addAll(model.getData());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<IklanRekomendasiFavoriteModel> call, Throwable throwable) {
                        Toast.makeText(getContext(), ""+ throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onTokenError() {
                Log.e("rekomendasi", "Failed to get token");
            }
        });
    }

    @Override
    public void onItemClick(IklanRekomendasiFavoriteModel.IklanFavorit data) {
        openFragment(data);
    }
    private void openFragment(IklanRekomendasiFavoriteModel.IklanFavorit data){
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

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, viewBarangFragment, "ViewBarangFragment");
        transaction.commit();
    }
}
