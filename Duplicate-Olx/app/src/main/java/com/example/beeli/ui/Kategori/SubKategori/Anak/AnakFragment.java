package com.example.beeli.ui.Kategori.SubKategori.Anak;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.beeli.model.IklanModel;
import com.example.beeli.R;
import com.example.beeli.adapter.IklanAdapter;
import com.example.beeli.databinding.FragmentSubKategoriBinding;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Barang.ViewBarangFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnakFragment extends Fragment implements IklanAdapter.OnItemClickListener {
    FragmentSubKategoriBinding binding;
    private List<Map<String, IklanModel.Iklan>> list = new ArrayList<>();
    private String subKategori;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSubKategoriBinding.inflate(inflater, container, false);
        view();

        return binding.getRoot();
    }


    private void view(){
        list.clear();
        subKategori = getArguments().getString("subKategori");
        binding.path.setText("Beranda / Perlengkapan Bayi & Anak / "+ subKategori);

        IklanAdapter adapter = new IklanAdapter(getContext(), list, "Iklan");
        adapter.setOnClickItemListener(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if (subKategori.equals("Semua")){
            getIklan(adapter, "Boneka & Mainan Anak");
            getIklan(adapter, "Buku Anak");
            getIklan(adapter, "Lain-Lain");
            getIklan(adapter, "Pakaian");
            getIklan(adapter, "Perlengkapan Bayi");
            getIklan(adapter, "Perlengkapan Ibu Bayi");
            getIklan(adapter, "Stroller");
        } else {
            getIklan(adapter, subKategori);
        }
    }

    private void getIklan(IklanAdapter adapter, String subKategori) {
        progressDialog();
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).getIAnak(subKategori).enqueue(new Callback<IklanModel>() {
                    @Override
                    public void onResponse(Call<IklanModel> call, Response<IklanModel> response) {
                        if (response.isSuccessful()) {
                            disableDialog();
                            IklanModel model = response.body();
                            if (model != null) {
                                list.addAll(model.getData());
                                adapter.notifyDataSetChanged();
                            }
                        }
                        disableDialog();
                    }

                    @Override
                    public void onFailure(Call<IklanModel> call, Throwable throwable) {
                        disableDialog();
                        Log.d("badEnding", "onFailure: "+ throwable);
                    }
                });
            }

            @Override
            public void onTokenError() {
                disableDialog();
            }
        });

    }
    @Override
    public void onItemClick(Object data) {
        openFragment((IklanModel.Iklan) data);
    }
    private void openFragment(IklanModel.Iklan data){
        progressDialog();
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

        viewBarangFragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, viewBarangFragment, "ViewBarangFragment");
        transaction.commit();
        disableDialog();
    }
    private void progressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    private void disableDialog() {
        dialog.dismiss();
    }
}