package com.example.beeli.ui.Login.Otp;

import static com.example.beeli.ui.Login.FormLogin.emailUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beeli.R;
import com.example.beeli.databinding.ActivityRegisterOtpBinding;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.ui.Login.LoginAccount;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtp extends AppCompatActivity {
    private ActivityRegisterOtpBinding binding;
    private AlertDialog dialog;
    private int resendClickCount = 0;
    Button resendOTPbtn;
    private boolean isCooldownActive = false;
    private CountDownTimer cooldownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView(){
        resendOTPbtn = binding.resendOtp;
        binding.otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend();
            }
        });

    }

    private void verify(){
        progressDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("email", emailUser);
        builder.addFormDataPart("code", binding.otp.getText().toString());

        RequestBody requestBody = builder.build();
        ApiService.endpoint().verifyOTP(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    disableDialog();
                    Toast.makeText(VerifyOtp.this, "Berhasil", Toast.LENGTH_SHORT);
                    finish();
                    Intent intent = new Intent(VerifyOtp.this, LoginAccount.class);
                    startActivity(intent);
                }
                else{
                    Log.d("response", "gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(VerifyOtp.this, "Anda Sedang Offline", Toast.LENGTH_SHORT);
                Log.d("Offline", "Response:" + throwable.getMessage());
            }
        });
    }

    private void resend() {
        if (isCooldownActive) {
            Toast.makeText(this, "Please wait for the cooldown period to end.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (resendClickCount < 2) {
            resendClickCount++;
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            builder.addFormDataPart("email", emailUser);

            RequestBody requestBody = builder.build();
            ApiService.endpoint().resendOTP(requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(VerifyOtp.this, "OTP Berhasil terkirim", Toast.LENGTH_SHORT).show();
                        if (resendClickCount == 2) {
                            startCooldown();
                        }
                    } else {
                        Log.d("response", "gagal");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Toast.makeText(VerifyOtp.this, "Anda Sedang Offline", Toast.LENGTH_SHORT).show();
                    Log.d("Offline", "Response:" + throwable.getMessage());
                }
            });
        } else {
            startCooldown();
        }
    }
    private void startCooldown() {
        isCooldownActive = true;
        resendClickCount = 0;
        resendOTPbtn.setEnabled(false);
        cooldownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format("Cooldown: %02d detik", seconds);
                resendOTPbtn.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                isCooldownActive = false;
                resendOTPbtn.setEnabled(true);
                resendOTPbtn.setText("Resend OTP");
                Toast.makeText(VerifyOtp.this, "Cooldown selesai, Anda dapat resend OTP sekarang.", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void progressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOtp.this);
        LayoutInflater inflater = VerifyOtp.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    private void disableDialog(){
        dialog.dismiss();
    }
}
