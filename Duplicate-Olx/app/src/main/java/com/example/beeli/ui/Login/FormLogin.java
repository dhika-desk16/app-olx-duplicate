package com.example.beeli.ui.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.databinding.ActivityFormPengisianDataBinding;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Login.Otp.VerifyOtp;
import com.example.beeli.ui.Lokasi.ProvinceActivity;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormLogin extends AppCompatActivity {
    public static String emailUser, password;
    private String name, email, pass, cPassword, hp, alamat, Province, Regency, District, Village, province, district, regency, village;
    private ActivityFormPengisianDataBinding binding;
    static boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormPengisianDataBinding.inflate(getLayoutInflater());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(binding.getRoot());
        initView();
        retrieveFormData();
        retrieveLokasi();
        checkPreferences();
    }

    private void initView() {
        binding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2; // Index for the drawable on the right side
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.confirmPassword.getRight() - binding.confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (!isPasswordVisible) {
                            // Show password
                            binding.confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            binding.confirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.confirmpasswordicon, 0, R.drawable.disabledpassword, 0); // Update drawable
                        } else {
                            // Hide password
                            binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            binding.confirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.confirmpasswordicon, 0, R.drawable.showpasswordicon, 0); // Update drawable
                        }
                        isPasswordVisible = !isPasswordVisible;
                        binding.confirmPassword.setSelection(binding.confirmPassword.getText().length());

                        // Handle click action for accessibility
                        v.performClick(); // Call performClick to ensure proper accessibility handling
                        return true;
                    }
                }
                return false;
            }
        });

        binding.password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2; // Index for the drawable on the right side
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.password.getRight() - binding.password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (!isPasswordVisible) {
                            // Show password
                            binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.passwordicon, 0, R.drawable.disabledpassword, 0); // Update drawable
                        } else {
                            // Hide password
                            binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.passwordicon, 0, R.drawable.showpasswordicon, 0); // Update drawable
                        }
                        isPasswordVisible = !isPasswordVisible;
                        binding.password.setSelection(binding.password.getText().length());

                        // Handle click action for accessibility
                        v.performClick(); // Call performClick to ensure proper accessibility handling
                        return true;
                    }
                }
                return false;
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.login.setEnabled(false);
                checkInput();
            }
        });

        binding.alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFormData();
                Intent intent = new Intent(FormLogin.this, ProvinceActivity.class);
                startActivity(intent);
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.login.setEnabled(false);
                checkInput();
                clearAllData();
                clearAllDataLokasi();
            }
        });
    }


    private void checkPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("LokasiUser", MODE_PRIVATE);
        if (sharedPreferences.contains("Province")) {
            binding.text7.setVisibility(View.VISIBLE);
            binding.text7.setText("Kecamatan : " + district + " " + "\nKelurahan : " + village + " " + "\nKota/Kab : " + " " + regency + " " + "\nProvinsi : " + province);
        } else {
            binding.text7.setVisibility(View.GONE);
        }
    }

    private void saveFormData() {
        String username = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        String cpassword = binding.confirmPassword.getText().toString();
        String hp = binding.noHp.getText().toString();
        String kecamatan = binding.alamat.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("SignUpForm", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username);
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("ConfirmPassword", cpassword);
        editor.putString("Nohp", hp);
        editor.putString("Kecamatan",kecamatan);
        editor.apply();
        Toast.makeText(this, "Form Data Saved", Toast.LENGTH_SHORT).show();
    }

    private void retrieveFormData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SignUpForm", MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");
        String email = sharedPreferences.getString("Email", "");
        String password = sharedPreferences.getString("Password", "");
        String cpassword = sharedPreferences.getString("ConfirmPassword", "");
        String hp = sharedPreferences.getString("Nohp", "");

        binding.name.setText(username);
        binding.email.setText(email);
        binding.password.setText(password);
        binding.confirmPassword.setText(cpassword);
        binding.noHp.setText(hp);
    }

    private void retrieveLokasi(){
        SharedPreferences sharedPreferences = getSharedPreferences("LokasiUser", MODE_PRIVATE);
        province = sharedPreferences.getString("Province", "");
        district = sharedPreferences.getString("District", "");
        regency = sharedPreferences.getString("Regencies", "");
        village = sharedPreferences.getString("Village", "");

        binding.text7.setText("Kecamatan : " +regency + "Kelurahan : "+village + "Kota / Kab : "+ district + "Provinsi : " + province);
    }

    private void clearAllData() {
        SharedPreferences sharedPreferences = FormLogin.this.getSharedPreferences("SignUpForm", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void clearAllDataLokasi() {
        SharedPreferences sharedPreferences = FormLogin.this.getSharedPreferences("LokasiUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void checkInput() {
        //        get the variabels
        name = binding.name.getText().toString();
        email = binding.email.getText().toString();
        pass = binding.password.getText().toString();
        cPassword = binding.confirmPassword.getText().toString();
        hp = binding.noHp.getText().toString();
        alamat = binding.text7.getText().toString();

        boolean b = true;

        if (name.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Nama", Toast.LENGTH_SHORT).show();
            binding.name.setError("Tidak boleh kosong");
            b = false;}
        else if (email.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Email", Toast.LENGTH_SHORT).show();
            binding.email.setError("Tidak boleh kosong");
            b = false;
        } else if (pass.isEmpty() || pass.length() < 6) {
            Toast.makeText(this, "Mohon mengisi Password dengan benar", Toast.LENGTH_SHORT).show();
            binding.password.setError("Tidak boleh kosong / minimal 6 char");
            b = false;
        } else if (!Objects.equals(cPassword, pass)) {
            Toast.makeText(this, "Confirm Password salah !", Toast.LENGTH_SHORT).show();
            binding.confirmPassword.setError("Harus sama dengan Password");
            b = false;
        } else if (hp.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Nomor Telepon", Toast.LENGTH_SHORT).show();
            binding.noHp.setError("Tidak boleh kosong");
            b = false;
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Alamat", Toast.LENGTH_SHORT).show();
            binding.alamat.setError("Tidak boleh kosong");
            b = false;
        }

        if (!b) {
            binding.login.setEnabled(true);
            return;
        } else {
            emailUser = email;
            password = pass;
            register();
        }
    }

    private void register() {
        Helper.progressDialog(this);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        // Add other form fields
        builder.addFormDataPart("name", name);
        builder.addFormDataPart("email", emailUser);
        builder.addFormDataPart("password", password);
        builder.addFormDataPart("password_confirmation", cPassword);
        builder.addFormDataPart("num_phone", hp);
        builder.addFormDataPart("districts", district);
        builder.addFormDataPart("villages", village);
        builder.addFormDataPart("regencies", regency);
        builder.addFormDataPart("provinces", province);

        RequestBody requestBody = builder.build();

        ApiService.endpoint().register(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Helper.disableDialog();
                if (response.isSuccessful()) {
                    Login.login.finish();
                    finish();
                    Intent intent = new Intent(FormLogin.this, VerifyOtp.class);
                    startActivity(intent);
                    Toast.makeText(FormLogin.this, "OTP TERKIRIM", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormLogin.this, "Sepertinya ada yang salah", Toast.LENGTH_SHORT).show();
                    Log.d("BadEnding", "onResponse: " + response.code());
                    Log.d("BadEnding", "onResponse: " + response.errorBody());
                    Log.d("BadEnding", "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Helper.disableDialog();
                Toast.makeText(FormLogin.this, ""+throwable, Toast.LENGTH_SHORT).show();
                binding.login.setClickable(true);
            }
        });
    }
}