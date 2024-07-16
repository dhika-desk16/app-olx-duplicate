package com.example.beeli.ui.Login;

import static com.example.beeli.ui.Login.FormLogin.emailUser;
import static com.example.beeli.ui.Login.FormLogin.password;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beeli.R;
import com.example.beeli.databinding.LoginAccountTokenBinding;
import com.example.beeli.model.LoginModel;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Home.Drawer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAccount extends AppCompatActivity {
    private LoginAccountTokenBinding binding;
    public static LoginModel token;
    static boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginAccountTokenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.email.setFocusable(false);
        binding.password.setFocusable(false);
        binding.password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.email.setText(emailUser);
        binding.password.setText(password);
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

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("email", emailUser);
        builder.addFormDataPart("password", password);

        ApiService.endpoint().login(builder.build()).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    LoginModel loginModel = response.body();
                    if (loginModel != null && loginModel.getData() != null) {
                        String token = loginModel.getData().getToken();
                        ApiService.setToken(token);
                        Toast.makeText(LoginAccount.this, "Berhasil Membuat Akun", Toast.LENGTH_SHORT).show();
                        Log.d("responLogin", "onResponse: "+response.message());
                        Log.d("responLogin", "onResponse: "+response.body());
                        Log.d("responLogin", "token: "+token);
                        finish();
                        Intent intent = new Intent(LoginAccount.this, Drawer.class);
                        startActivity(intent);
                    } else {
                        Log.d("responLogin", "Data or Token is null :" + token);
                    }
                }
                else {
                    Log.d("responLogin", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                Log.d("responLogin", "onResponse: "+throwable.getMessage());
            }
        });
    }
}