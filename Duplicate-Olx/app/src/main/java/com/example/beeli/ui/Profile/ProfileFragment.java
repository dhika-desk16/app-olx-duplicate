package com.example.beeli.ui.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.beeli.R;
import com.example.beeli.databinding.ActivityProfilBinding;
import com.example.beeli.model.ProfileModel;
import com.example.beeli.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ActivityProfilBinding binding;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityProfilBinding.inflate(inflater, container, false);
        view();

        return binding.getRoot();
    }

    private void view() {
        getProfile();
        Toolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(drawer.findViewById(R.id.nav_drawer));
            }
        });

        binding.edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, new FormProfile(), "FormFragment");
                transaction.commit();
            }
        });
    }

    private void getProfile() {
        progressDialog();
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).getProfile().enqueue(new Callback<ProfileModel>() {
                    @Override
                    public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            disableDialog();
                            ProfileModel profileModel = response.body();
                            ProfileModel.User user = profileModel.getData();

                            binding.userName.setText(user.getName());

                            Bitmap decodedImage = decodeBase64Image(user.getPict_profile());
                            binding.foto.setImageBitmap(decodedImage);
                        } else {
                            disableDialog();
                            Log.d("getUserProfile", "Response Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileModel> call, Throwable throwable) {
                        disableDialog();
                        Log.d("getUserProfile", "Failed to fetch user ta" + throwable.getMessage());
                    }
                });
            }

            @Override
            public void onTokenError() {
                disableDialog();
                Toast.makeText(getContext(), "Failed to get token", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Bitmap decodeBase64Image(String base64Image) {
        if (base64Image == null) {
            return null;
        }
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private void progressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ProfileFragment.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    private void disableDialog() {
        dialog.dismiss();
    }
}