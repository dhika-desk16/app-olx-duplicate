package com.example.beeli.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.databinding.LoginAccountTokenBinding;
import com.example.beeli.model.LoginModel;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Home.Drawer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUser extends AppCompatActivity {
    private LoginAccountTokenBinding binding;
    public static LoginModel token;
    private String email, password;
    static boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginAccountTokenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUser.this, FormLogin.class);
                startActivity(intent);
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
                            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.disabledpassword, 0); // Update drawable
                        } else {
                            // Hide password
                            binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.showpasswordicon, 0); // Update drawable
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
    }


    private void checkInput(){
        //        get the variabels
        email = binding.email.getText().toString();
        password = binding.password.getText().toString();

        boolean b = true;

        if (email.isEmpty()) {
            Toast.makeText(this, "Mohon mengisi Email", Toast.LENGTH_SHORT).show();
            binding.email.setError("Tidak boleh kosong");
            b = false;
        } else if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(this, "Mohon mengisi Password dengan benar", Toast.LENGTH_SHORT).show();
            binding.password.setError("Tidak boleh kosong / minimal 6 char");
            b = false;
        }

        if (!b) {
            binding.login.setEnabled(true);
            return;
        } else {
            login();
        }
    }


    private void login() {
        Helper.progressDialog(this);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("password", password);

        ApiService.endpoint().login(builder.build()).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Helper.disableDialog();
                if(response.isSuccessful()){
                    LoginModel loginModel = response.body();
                    if (loginModel != null && loginModel.getData() != null) {
                        String token = loginModel.getData().getToken();
                        ApiService.setToken(token);
                        Toast.makeText(LoginUser.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                        Log.d("responLogin", "onResponse: "+response.message());
                        Log.d("responLogin", "onResponse: "+response.body());
                        Log.d("responLogin", "token: "+token);
                        finish();
                        Intent intent = new Intent(LoginUser.this, Drawer.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginUser.this, "Token Error", Toast.LENGTH_SHORT).show();
                        Log.d("responLogin", "Data or Token is null :" + token);
                    }
                }
                else {
                    Helper.disableDialog();
                    Toast.makeText(LoginUser.this, "Maaf, Akun Anda Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    Log.d("responLogin", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                Helper.disableDialog();
                Toast.makeText(LoginUser.this, ""+ throwable, Toast.LENGTH_SHORT).show();
                Log.d("responLogin", "onResponse: "+throwable.getMessage());
            }
        });
    }
}