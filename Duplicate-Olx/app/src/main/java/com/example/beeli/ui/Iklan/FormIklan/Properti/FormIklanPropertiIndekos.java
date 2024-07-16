package com.example.beeli.ui.Iklan.FormIklan.Properti;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.databinding.FormTambahIklanPropertiBinding;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Iklan.PilihKategoriIklan;
import com.example.beeli.ui.Iklan.SubKategoriIklan.PropertiIklan;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormIklanPropertiIndekos extends AppCompatActivity {
    FormTambahIklanPropertiBinding binding;
    private String alamat, luasBangunan, kamarMandi, fasilitas, judul_iklan, deskripsi, harga;
    private int uploadedCount, queue = 1;
    List<String> selectedFacilities = new ArrayList<>();
    private Uri selectedImageUri;
    private File[] imagesFile = new File[12];
    private CardView[] cardViews = new CardView[12];
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FormTambahIklanPropertiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        view();
    }



    private void view() {
        //        set soft input mode adjust pan
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding.kategori.setText("Properti / Indekos");
        binding.ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormIklanPropertiIndekos.this, PilihKategoriIklan.class);
                            PilihKategoriIklan.menuKategoriIklan.finish();
                            PropertiIklan.subProperti.finish();
                startActivity(intent);
            }
        });
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.linearLayout8.setVisibility(View.GONE);
        binding.propertiKamarMandi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kamarMandi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadImage();
        deleteImage();

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnSimpan.setEnabled(false);
                binding.btnSimpan.setText("Menyimpan...");
                checkInput();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            handleImageSelection(queue);
            queue++;
        }
    }

    private void uploadImage() {
        // Initialize CardViews
        cardViews[0] = binding.include.cardFoto1;
        cardViews[1] = binding.include.cardFoto2;
        cardViews[2] = binding.include.cardFoto3;
        cardViews[3] = binding.include.cardFoto4;
        cardViews[4] = binding.include.cardFoto5;
        cardViews[5] = binding.include.cardFoto6;
        cardViews[6] = binding.include.cardFoto7;
        cardViews[7] = binding.include.cardFoto8;
        cardViews[8] = binding.include.cardFoto9;
        cardViews[9] = binding.include.cardFoto10;
        cardViews[10] = binding.include.cardFoto11;
        cardViews[11] = binding.include.cardFoto12;

        for (int i = 0; i < cardViews.length; i++) {
            final int index = i;
            cardViews[i].setOnClickListener(v -> {
                if (cardViews[index].isClickable()) {
                    // Temporarily disable the card view
                    cardViews[index].setClickable(false);

                    // Start the image selection process
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                    // Re-enable the card view after a short delay if no image is inserted
                    new Handler().postDelayed(() -> {
                        if (imagesFile[index] == null) {
                            cardViews[index].setClickable(true);
                        }
                    }, 1000); // Delay in milliseconds (1 seconds)
                }
            });
        }
    }

    private void handleImageSelection(int ueue) {
        if (selectedImageUri != null && ueue <= 12) {
            try {
                // Check the file size
                Cursor returnCursor = getContentResolver().query(selectedImageUri, null, null, null, null);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                long fileSize = returnCursor.getLong(sizeIndex);
                returnCursor.close();

                long maxSize = 5 * 1024 * 1024; // 5 MB

                if (fileSize > maxSize) {
                    // Show an error message
                    Toast.makeText(this, "Ukuran File melebihi 5 MB", Toast.LENGTH_SHORT).show();
                    queue--;
                    return; 
                }

                // Convert URI to File
                File imageFile = uriToFile(selectedImageUri);

                if (imageFile != null) {
                    imagesFile[ueue - 1] = imageFile;

                    // Update the ImageView with the selected image
                    ImageView imageView = findViewById(getResources().getIdentifier("foto" + ueue, "id", getPackageName()));
                    if (imageView != null) {
                        Picasso.get().load(selectedImageUri).fit().centerCrop().into(imageView);

                        float dp = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                100, // 100dp
                                getResources().getDisplayMetrics()
                        );
                        imageView.getLayoutParams().width = (int) dp;
                        imageView.getLayoutParams().height = (int) dp;
                        imageView.requestLayout();

                        ImageView deleteButton = findViewById(getResources().getIdentifier("deleteFoto" + ueue, "id", getPackageName()));
                        if (deleteButton != null) {
                            deleteButton.setVisibility(View.VISIBLE);
                        }

                        uploadedCount++;
                        if (uploadedCount >= 3 && imagesFile[0] != null && imagesFile[1] != null && imagesFile[2] != null) {
                            binding.btnSimpan.setEnabled(true);
                            binding.btnSimpan.setBackgroundColor(getResources().getColor(R.color.blue));
                        }

                        // Disable the CardView that has the image inserted
                        cardViews[ueue - 1].setClickable(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Image Full!", Toast.LENGTH_SHORT).show();
        }
    }

    private File uriToFile(Uri uri) {
        if (uri == null) return null;

        try {
            // Create a temporary file
            File tempFile = File.createTempFile("image_", null, getCacheDir());
            // Open input stream for the URI
            InputStream inputStream = getContentResolver().openInputStream(uri);
            // Open output stream to the temporary file
            OutputStream outputStream = new FileOutputStream(tempFile);
            // Copy the contents of the input stream to the output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // Close streams
            outputStream.close();
            inputStream.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteImage() {
        for (int i = 1; i <= 12; i++) {
            int position = i - 1;
            ImageView deleteButton = findViewById(getResources().getIdentifier("deleteFoto" + i, "id", getPackageName()));
            if (deleteButton != null) {
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position < imagesFile.length && imagesFile[position] != null) {
                            imagesFile[position] = null;
                            uploadedCount--;
                            queue--;

                            cardViews[position].setClickable(true);
                            deleteButton.setVisibility(View.GONE);
                        }
                        updateImageViews();
                    }
                });
            }
        }
    }

    private void updateImageViews() {
        // Rearrange the images in the array and update the ImageViews
        File[] newImagesFile = new File[12];
        int newIndex = 0;

        for (File imageFile : imagesFile) {
            if (imageFile != null) {
                newImagesFile[newIndex++] = imageFile;
            }
        }
        imagesFile = newImagesFile;

        // Update ImageViews with the new images array
        for (int i = 0; i < imagesFile.length; i++) {
            ImageView imageView = findViewById(getResources().getIdentifier("foto" + (i + 1), "id", getPackageName()));
            ImageView deleteButton = findViewById(getResources().getIdentifier("deleteFoto" + (i + 1), "id", getPackageName()));

            if (imageView != null) {
                if (i < newIndex) {
                    Picasso.get().load(imagesFile[i]).fit().centerCrop().into(imageView);
                    float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                    imageView.getLayoutParams().width = (int) dp;
                    imageView.getLayoutParams().height = (int) dp;
                    imageView.requestLayout();
                    if (deleteButton != null) {
                        deleteButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    imageView.setImageResource(R.drawable.materialsymbolscameraenhanceoutline);
                    float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                    imageView.getLayoutParams().width = (int) dp;
                    imageView.getLayoutParams().height = (int) dp;
                    imageView.requestLayout();
                    if (deleteButton != null) {
                        deleteButton.setVisibility(View.GONE);
                    }
                }
            }
        }

        // Update the UI state
        binding.btnSimpan.setEnabled(uploadedCount >= 3);
    }

    //    check Input for Request
    private void checkInput() {
        judul_iklan = binding.propertiJudul.getText().toString();
        deskripsi = binding.deskripsiProperti.getText().toString();
        alamat = binding.alamatLokasi.getText().toString();
        luasBangunan = binding.propertiLuasBangunan.getText().toString();
        harga = binding.propertiHarga.getText().toString();

        boolean b = true;

        if (judul_iklan.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Judul Iklan", Toast.LENGTH_SHORT).show();
            binding.propertiJudul.setError("Tidak boleh kosong");
            b = false;
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Alamat", Toast.LENGTH_SHORT).show();
            binding.alamatLokasi.setError("Tidak boleh kosong");
            b = false;
        } else if (kamarMandi.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Kamar Mandi", Toast.LENGTH_SHORT).show();
            binding.alamatLokasi.setError("Tidak boleh kosong");
            b = false;
        }else if (luasBangunan.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Luas Bangunan", Toast.LENGTH_SHORT).show();
            binding.propertiLuasBangunan.setError("Tidak boleh kosong");
            b = false;
        } else if (deskripsi.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Deskripsi Barang", Toast.LENGTH_SHORT).show();
            binding.deskripsiProperti.setError("Tidak boleh kosong");
            b = false;
        } else if (harga.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Harga Barang", Toast.LENGTH_SHORT).show();
            binding.propertiHarga.setError("Tidak boleh kosong");
            b = false;
        } else if (uploadedCount < 3) {
            Toast.makeText(this, "Mohon mengisi minimal 3 Gambar Produk", Toast.LENGTH_SHORT).show();
            b = false;
        }

        if (!b) {
            binding.btnSimpan.setEnabled(true);
            binding.btnSimpan.setText("Pasang Iklan Sekarang");
            return;
        }

        if (uploadedCount >= 3 && uploadedCount < 12) {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin melanjutkan tanpa mengisi penuh Gambar produk?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            post();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked No button, do nothing
                            dialog.dismiss();
                            binding.btnSimpan.setEnabled(true);
                            binding.btnSimpan.setText("Pasang Iklan Sekarang");
                        }
                    })
                    .show();
        } else {
            post();
        }
    }

    private void post() {
         Helper.progressDialog(this);
        if (binding.AC.isChecked()) {
            selectedFacilities.add(binding.AC.getText().toString());
        }
        if (binding.swimmingPool.isChecked()) {
            selectedFacilities.add(binding.swimmingPool.getText().toString());
        }
        if (binding.carPort.isChecked()) {
            selectedFacilities.add(binding.carPort.getText().toString());
        }
        if (binding.waterHeater.isChecked()) {
            selectedFacilities.add(binding.waterHeater.getText().toString());
        }
        if (binding.garasi.isChecked()) {
            selectedFacilities.add(binding.garasi.getText().toString());
        }
        if (binding.telephone.isChecked()) {
            selectedFacilities.add(binding.telephone.getText().toString());
        }
        if (binding.pam.isChecked()) {
            selectedFacilities.add(binding.pam.getText().toString());
        }
        if (binding.refrigerator.isChecked()) {
            selectedFacilities.add(binding.refrigerator.getText().toString());
        }
        if (binding.stove.isChecked()) {
            selectedFacilities.add(binding.stove.getText().toString());
        }
        if (binding.microwave.isChecked()) {
            selectedFacilities.add(binding.microwave.getText().toString());
        }
        if (binding.oven.isChecked()) {
            selectedFacilities.add(binding.oven.getText().toString());
        }
        if (binding.fireExtenguisher.isChecked()) {
            selectedFacilities.add(binding.fireExtenguisher.getText().toString());
        }
        if (binding.gordyn.isChecked()) {
            selectedFacilities.add(binding.gordyn.getText().toString());
        }
        fasilitas = TextUtils.join(",", selectedFacilities);


        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("fasilitas", fasilitas);
        builder.addFormDataPart("luas_bangunan", luasBangunan);
        builder.addFormDataPart("kamar_mandi", kamarMandi);
        builder.addFormDataPart("judul_iklan", judul_iklan);
        builder.addFormDataPart("deskripsi", deskripsi);
        builder.addFormDataPart("harga", harga);

        for (int i = 0; i < imagesFile.length; i++) {
            if (imagesFile[i] != null) {
                File image = imagesFile[i];
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), image);
                builder.addFormDataPart("gambar" + (i + 1), image.getName(), body);
            }
        }
        RequestBody requestBody = builder.build();

        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                ApiService.endpoint(token).postIProperti("indekos", requestBody).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                             Helper.disableDialog();
                            Intent intent = new Intent(FormIklanPropertiIndekos.this, PilihKategoriIklan.class);
                            PilihKategoriIklan.menuKategoriIklan.finish();
                            PropertiIklan.subProperti.finish();
                            startActivity(intent);
                        } else {
                             Helper.disableDialog();
                            binding.btnSimpan.setEnabled(true);
                            binding.btnSimpan.setText("Pasang Iklan Sekarang");
                            Log.d("IklanPropertiIndekos", "onResponse: " + response.code());
                            Log.d("IklanPropertiIndekos", "onResponse: " + response.message());
                            Toast.makeText(FormIklanPropertiIndekos.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                         Helper.disableDialog();
                        binding.btnSimpan.setEnabled(true);
                        binding.btnSimpan.setText("Pasang Iklan Sekarang");
                        Log.d("IklanPropertiIndekos", "onResponse Failure: " + throwable.getMessage());
                        Toast.makeText(FormIklanPropertiIndekos.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onTokenError() {
                 Helper.disableDialog();
                Toast.makeText(FormIklanPropertiIndekos.this, "Failed to get token", Toast.LENGTH_SHORT).show();
            }
        });

    }
 
}
