package com.example.beeli.ui.Barang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.databinding.ViewbarangBinding;
import com.example.beeli.retrofit.ApiService;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBarangFragment extends Fragment {
    ViewbarangBinding binding;
    private String kodeIklan, judul, name, gaji_dari, gaji_sampai, kondisi, tipe, luas_tanah,
            luas_bangunan, sertifikasi, fasilitas, kamar_mandi, kamar_tidur, lantai, alamat_lokasi;
    private int harga;
    private boolean isFavorite = false;
    private String[] gambarArray;
    private List<String> deskripsi = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewbarangBinding.inflate(inflater, container, false);
        view();

        return binding.getRoot();
    }

    private void view(){
        binding.bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(drawer.findViewById(R.id.nav_drawer));
            }
        });
//        Animasi untuk Toolbarfooter
        Animation slideUpAnimation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.slide_up);
        binding.toolbarFooter.startAnimation(slideUpAnimation);
        binding.toolbarFooter.setVisibility(View.VISIBLE);

        binding.laporkanIklan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportDialog();
            }
        });
        shareContent();

//        Menghilangkan toolbar Footer ketika page berada di footer
        binding.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            boolean isFooterVisible = true;

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = binding.scrollView.getChildAt(0);
                int bottomMargin = contentView.getMeasuredHeight() - binding.scrollView.getHeight();

                if (scrollY >= bottomMargin) {
                    if (isFooterVisible) {
                        isFooterVisible = false;
                        Animation slideDownAnimation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.slide_down);
                        slideDownAnimation.setStartOffset(200);
                        binding.toolbarFooter.startAnimation(slideDownAnimation);
                        binding.toolbarFooter.setVisibility(View.GONE);
                    }
                } else {
                    if (!isFooterVisible) {
                        isFooterVisible = true;
                        Animation slideUpAnimation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.slide_up);
                        binding.toolbarFooter.startAnimation(slideUpAnimation);
                        slideUpAnimation.setStartOffset(800);
                        binding.toolbarFooter.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        Log.d("ending", "view: " + getArguments());

//        get the value
        if (getArguments() != null){
            kodeIklan = getArguments().getString("kodeIklan");
            judul = getArguments().getString("judul_iklan");
            harga = getArguments().getInt("harga");
            name = getArguments().getString("name");
            gambarArray = getArguments().getStringArray("gambarArray");
            gaji_dari = getArguments().getString("gaji_dari");
            gaji_sampai = getArguments().getString("gaji_sampai");

            kondisi = getArguments().getString("kondisi");
            tipe = getArguments().getString("tipe");
            luas_tanah = getArguments().getString("luas_tanah");
            luas_bangunan = getArguments().getString("luas_bangunan");
            sertifikasi = getArguments().getString("sertifikasi");
            fasilitas = getArguments().getString("fasilitas");
            kamar_mandi = getArguments().getString("kamar_mandi");
            kamar_tidur = getArguments().getString("kamar_tidur");
            lantai = getArguments().getString("lantai");
            alamat_lokasi = getArguments().getString("alamat-lokasi");

            if (kondisi != null) {
                deskripsi.add(kondisi);
            }
            if (tipe != null) {
                deskripsi.add("Tipe : " + tipe);
            }
            if (luas_tanah != null || luas_bangunan != null || sertifikasi != null || fasilitas != null
                    || kamar_mandi != null || kamar_tidur != null || lantai != null || alamat_lokasi != null) {

                deskripsi.add("Luas Tanah : " + luas_tanah);
                deskripsi.add("Luas Bangunan : " + luas_bangunan);
                deskripsi.add("Sertifikasi : " + sertifikasi);
                deskripsi.add("Fasilitas : " + fasilitas);
                deskripsi.add("Kamar Mandi : " + kamar_mandi);
                deskripsi.add("Kamar Tidur : " + kamar_tidur);
                deskripsi.add("Lantai : " + lantai);
                deskripsi.add("Alamat Lokasi : " + alamat_lokasi);
            }
            deskripsi.add(getArguments().getString("deskripsi"));
            String deskrips = TextUtils.join("\n\n", deskripsi);

            if (gaji_dari != null && gaji_sampai != null){
                binding.hargaBarang.setText("Rp "+ Helper.getCurrencyFormat(Integer.parseInt(gaji_dari)) +" - "+ Helper.getCurrencyFormat(Integer.parseInt(gaji_dari)));
            } else {
            binding.hargaBarang.setText("Rp "+ Helper.getCurrencyFormat(harga));
            }
            binding.judulBarang.setText(judul);
            binding.userName.setText(name);
            binding.deskripsiBarang.setText(deskrips);
            binding.bahanBakar.setVisibility(View.GONE);

            ArrayList<SlideModel> slideModels = new ArrayList<>();
            if (gambarArray != null) {
                for (String base64String : gambarArray) {
                    File imageFile = decodeBase64ToFile(getContext(), base64String);
                    if (imageFile != null) {
                        Uri imageUri = Uri.fromFile(imageFile);
                        String imageUrl = imageUri.toString();

                        slideModels.add(new SlideModel(imageUrl, ScaleTypes.CENTER_INSIDE));
                    } else {
                        Log.e("SlideImage", "view: Image null");
                    }
                }
            }

            // Set the slideModels to the imageSlider
            binding.imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);
            binding.imageSlider.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemSelected(int i) {
                    SlideModel slideModel = slideModels.get(i);
                    showImageInLarge(slideModel.getImageUrl());
                }
            });
        }


        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.favorite.setClickable(false);
                if (isFavorite == false) {
                    binding.favorite.setBackgroundResource(R.drawable.favorit_ic_2);
                    postFavorite();
                } else {
                    binding.favorite.setBackgroundResource(R.drawable.favorit_ic);
                    deleteFavorite();
                }
            }
        });
    }

    private void showImageInLarge(String imageUrl) {
        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image);

        PhotoView photoView = dialog.findViewById(R.id.photo_view);
        Picasso.get().load(imageUrl).into(photoView);

        ImageView closeImg = dialog.findViewById(R.id.close_image);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void showReportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_report, null);
        builder.setView(view);

        EditText editTextReason = view.findViewById(R.id.edit_text_reason);
        Button buttonCancel = view.findViewById(R.id.button_cancel);
        Button buttonSend = view.findViewById(R.id.button_send);

        AlertDialog dialog = builder.create();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = editTextReason.getText().toString();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void shareContent(){
        ImageView shareBtn = binding.share;
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Tes";
                String Sub = "Halo Dunia";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Bagikan Iklan Dengan"));
            }
        });
    }
    private void postFavorite(){
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).postFavorite(kodeIklan).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Ditambahkan ke Favorit", Toast.LENGTH_SHORT).show();
                            isFavorite = true;
                        } else {
                            binding.favorite.setBackgroundResource(R.drawable.favorit_ic);
                            Toast.makeText(getContext(), "Gagal menambahkan ke Favorit", Toast.LENGTH_SHORT).show();
                            isFavorite = false;
                        }
                        binding.favorite.setClickable(true);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        binding.favorite.setBackgroundResource(R.drawable.favorit_ic);
                        binding.favorite.setClickable(true);
                        isFavorite = false;
                        Log.d("BadEnding", "onFailure: "+ throwable);
                    }
                });
            }

            @Override
            public void onTokenError() {
                binding.favorite.setBackgroundResource(R.drawable.favorit_ic);
                binding.favorite.setClickable(true);
                Toast.makeText(getContext(), "Silahkan Login terlebih dahulu !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteFavorite(){
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).deleteFavorite(kodeIklan).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Dihapus dari Favorit", Toast.LENGTH_SHORT).show();
                            isFavorite = false;
                        } else {
                            binding.favorite.setBackgroundResource(R.drawable.favorit_ic_2);
                            Toast.makeText(getContext(), "Gagal menghapus dari Favorit", Toast.LENGTH_SHORT).show();
                            isFavorite = true;
                        }
                        binding.favorite.setClickable(true);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        binding.favorite.setBackgroundResource(R.drawable.favorit_ic_2);
                        binding.favorite.setClickable(true);
                        isFavorite = true;
                        Log.d("BadEnding", "onFailure: "+ throwable);
                    }
                });
            }

            @Override
            public void onTokenError() {
                binding.favorite.setBackgroundResource(R.drawable.favorit_ic_2);
                binding.favorite.setClickable(true);
                Toast.makeText(getContext(), "Silahkan Login terlebih dahulu !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static File decodeBase64ToFile(Context context, String base64Str) {
        if (base64Str == null || base64Str.isEmpty()) {
            return null;
        }

        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        if (bitmap == null) {
            Log.e("decodeBase64ToFile", "Failed to decode Base64 string into Bitmap");
            return null;
        }

        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, System.currentTimeMillis() + ".png");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            Log.e("decodeBase64ToFile", "Error saving bitmap to file", e);
            return null;
        }

        return imageFile;
    }
}