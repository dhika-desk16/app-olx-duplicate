package com.example.beeli.ui.Profile;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.databinding.BottomSheetProfileBinding;
import com.example.beeli.databinding.FormProfilBinding;
import com.example.beeli.model.ProfileModel;
import com.example.beeli.retrofit.ApiService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormProfile extends Fragment {
    FormProfilBinding binding;
    private Uri selectedImageUri, croppedUri;
    private String nama, tentang_saya, email, alamat, telepon, encodedImage;
    private Bitmap decodedImage;
    public static final int PICK_IMAGE = 1;
    private static final int UCROP_REQUEST_CODE = 1001;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FormProfilBinding.inflate(inflater, container, false);
        view();

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("FormProfile", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            startUCrop(selectedImageUri);
        } else if (requestCode == UCROP_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            handleUCrop(data);
        }
    }

    private void view() {
        getProfile();
        retrieveFormData();

        Toolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(drawer.findViewById(R.id.nav_drawer));
            }
        });
        binding.fotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showottomSheet();
            }
        });
        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnSimpan.setEnabled(false);
                binding.btnSimpan.setText("Menyimpan....");
                saveFormData();
                checkInput();
            }
        });
        binding.btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void saveFormData() {
        String nama = binding.nama.getText().toString();
        String tentang = binding.tentangSaya.getText().toString();
        String noHp = binding.telepon.getText().toString();
        String email = binding.email.getText().toString();
        String alamat = binding.alamat.getText().toString();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ProfileAccount", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Nama", nama);
        editor.putString("TentangSaya", tentang);
        editor.putString("NoHp", noHp);
        editor.putString("Email", email);
        editor.putString("Alamat", alamat);
        editor.apply();
        Toast.makeText(getContext(), "Alamat telah disimpan", Toast.LENGTH_SHORT).show();
    }

    private void retrieveFormData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ProfileAccount", MODE_PRIVATE);
        String nama = sharedPreferences.getString("Nama", "");
        String tentang = sharedPreferences.getString("TentangSaya", "");
        String noHp = sharedPreferences.getString("NoHp", "");
        String email = sharedPreferences.getString("Email", "");
        String alamat = sharedPreferences.getString("Alamat", "");
        binding.nama.setText(nama);
        binding.tentangSaya.setText(tentang);
        binding.telepon.setText(noHp);
        binding.email.setText(email);
        binding.alamat.setText(alamat);
    }

    private void getProfile() {
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).getProfile().enqueue(new Callback<ProfileModel>() {
                    @Override
                    public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ProfileModel profileModel = response.body();
                            ProfileModel.User user = profileModel.getData();

                            binding.nama.setText(user.getName());
                            binding.tentangSaya.setText(user.getTentang_saya());
                            binding.email.setText(user.getEmail());
                            binding.telepon.setText(String.valueOf(user.getNum_phone()));

                            encodedImage = user.getPict_profile();
                            decodedImage = decodeBase64Image(user.getPict_profile());
                            binding.imageProfil.setImageBitmap(decodedImage);

                        } else {
                            Log.d("getUserProfile", "User data not found");
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

    private void postProfile() {
        Helper.progressDialog(getContext());
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                // Add other form fields
                builder.addFormDataPart("name", nama);
                builder.addFormDataPart("tentang_saya", tentang_saya);
                builder.addFormDataPart("email", email);
                builder.addFormDataPart("num_phone", telepon);
                builder.addFormDataPart("alamat", alamat);

                if (croppedUri != null) {
                    File imageFile = new File(croppedUri.getPath());
                    if (imageFile.exists()) {
                        // If new image uploaded, add it to the request
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                        builder.addFormDataPart("pict_profile", imageFile.getName(), requestBody);
                    } else {
                        // If croppedUri exists but the file does not exist, log an error
                        Log.e("postProfile", "Image file does not exist: " + croppedUri.getPath());
                    }
                } else {
                    // If no new image uploaded, add the existing encoded image to the request
                    if (decodedImage != null) {
                        File outputDir = getContext().getCacheDir(); // context is your activity or application context
                        File outputFile = new File(outputDir, "temp_image.png");
                        try {
                            outputFile.createNewFile();
                            FileOutputStream outputStream = new FileOutputStream(outputFile);
                            decodedImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), outputFile);
                        builder.addFormDataPart("pict_profile", outputFile.getName(), requestBody);
                    } else {
                        // If no encoded image exists, log an error
                        Log.e("postProfile", "No encoded image available");
                    }
                }

                RequestBody requestBody = builder.build();

                ApiService.endpoint(token).editProfile(requestBody).enqueue(new Callback<ProfileModel.User>() {
                    @Override
                    public void onResponse(Call<ProfileModel.User> call, Response<ProfileModel.User> response) {
                        if (response.isSuccessful()) {
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, new ProfileFragment());
                            transaction.commit();
                        } else {
                            Log.d("UserProfile", "error  :" + response.message());
                            binding.btnSimpan.setEnabled(true);
                            binding.btnSimpan.setText("Simpan");
                        }
                        Helper.disableDialog();
                    }

                    @Override
                    public void onFailure(Call<ProfileModel.User> call, Throwable throwable) {
                        Helper.disableDialog();
                        Log.d("UserProfile", "eoor mang  ." + throwable.getMessage());
                        binding.btnSimpan.setEnabled(true);
                        binding.btnSimpan.setText("Simpan");
                    }
                });
            }

            @Override
            public void onTokenError() {
                Helper.disableDialog();
                Toast.makeText(getActivity(), "Failed to get token", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkInput() {
        //        get the variabels
        nama = binding.nama.getText().toString();
        tentang_saya = binding.tentangSaya.getText().toString();
        email = binding.email.getText().toString();
        telepon = binding.telepon.getText().toString();
        alamat = binding.alamat.getText().toString();

        boolean b = true;

        if (nama.isEmpty()) {
            Toast.makeText(getActivity(), "Mohon mengisi Nama", Toast.LENGTH_SHORT).show();
            binding.nama.setError("Tidak boleh kosong");
            b = false;
        } else if (telepon.isEmpty()) {
            Toast.makeText(getActivity(), "Mohon mengisi Nomor Telepon", Toast.LENGTH_SHORT).show();
            binding.telepon.setError("Tidak boleh kosong");
            b = false;
        } else if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Mohon mengisi Email", Toast.LENGTH_SHORT).show();
            binding.telepon.setError("Tidak boleh kosong");
            b = false;
        }

        if (!b) {
            binding.btnSimpan.setEnabled(true);
            binding.btnSimpan.setText("Simpan");
            return;
        }

        if (selectedImageUri == null && encodedImage == null && decodedImage == null) {
            // Display a confirmation dialog to the user
            new AlertDialog.Builder(getActivity())
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin melanjutkan tanpa mengisi Foto Profil?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            postProfile();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked No button, do nothing
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            // If image is selected
            postProfile();
        }
    }

    private void showottomSheet() {
        BottomSheetProfileBinding botSheet = BottomSheetProfileBinding.inflate(getLayoutInflater(), null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(botSheet.getRoot());
        botSheet.btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                bottomSheetDialog.cancel();
            }
        });

        botSheet.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageProfil.setImageResource(0);
                selectedImageUri = null;
            }
        });

        bottomSheetDialog.show();
    }

    private Bitmap decodeBase64Image(String base64Image) {
        if (base64Image == null) {
            return null;
        }
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private void startUCrop(Uri sourceUri) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Uri destinationUri = Uri.fromFile(new File(requireActivity().getCacheDir(), "cropped_image" + timestamp));

        UCrop.of(sourceUri, destinationUri)
                .withMaxResultSize(500, 500)
                .withAspectRatio(1, 1)
                .withOptions(getCropOptions())
                .start(requireContext(), this, UCROP_REQUEST_CODE);
    }

    private void handleUCrop(Intent data) {
        // Retrieve the UCrop result URI
        Uri resultUri = UCrop.getOutput(data);
        if (resultUri != null) {
            // Handle the cropped image URI
            // For example, you can load it into an ImageView using Picasso
            Picasso.get().load(resultUri).fit().centerCrop().into(binding.imageProfil);
            // Update the croppedUri variable with the result URI
            croppedUri = resultUri;
        } else {
            Log.e("FormProfile", "handleUCropResult: Cropped URI is null");
        }
    }

    private UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true); // Set crop shape to circle
        options.setShowCropFrame(false); // Hide crop frame to make it look like a perfect circle
        options.setShowCropGrid(false); // Hide crop grid
        return options;
    }
}