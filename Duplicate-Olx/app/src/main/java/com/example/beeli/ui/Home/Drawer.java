package com.example.beeli.ui.Home;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.beeli.DeleteToken;
import com.example.beeli.Helper;
import com.example.beeli.R;
import com.example.beeli.databinding.ActivityDrawerBinding;
import com.example.beeli.model.ProfileModel;
import com.example.beeli.retrofit.ApiService;
import com.example.beeli.roomDB.TokenDatabase;
import com.example.beeli.roomDB.TokenEntity;
import com.example.beeli.ui.Iklan.PasangIklan;
import com.example.beeli.ui.IklanSayaFavorite.IklanSayaFavorite;
import com.example.beeli.ui.Kategori.SubKategori.Anak.AnakFragment;
import com.example.beeli.ui.Kategori.SubKategori.Elektronik.ElektronikFragment;
import com.example.beeli.ui.Kategori.SubKategori.Hobi.HobiFragment;
import com.example.beeli.ui.Kategori.SubKategori.Jasa.JasaFragment;
import com.example.beeli.ui.Kategori.SubKategori.Kantor.KantorFragment;
import com.example.beeli.ui.Kategori.SubKategori.Mobil.MobilFragment;
import com.example.beeli.ui.Kategori.SubKategori.Motor.MotorFragment;
import com.example.beeli.ui.Kategori.SubKategori.Pribadi.PribadiFragment;
import com.example.beeli.ui.Kategori.SubKategori.Properti.PropertiFragment;
import com.example.beeli.ui.Kategori.SubKategori.Rumah.RumahFragment;
import com.example.beeli.ui.Login.Login;
import com.example.beeli.ui.Profile.FormProfile;
import com.example.beeli.ui.Profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Drawer extends AppCompatActivity {
    private static TokenDatabase tokenDatabase;
    private ActivityDrawerBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String name, email, fragmentToLoad, subKategori;
    private View header;
    private MenuItem log_in_out;
    private ImageView userImage;
    private TextView uName;
    private TextView uEmail;
    private boolean doubleBackToExitPressedOnce = false;

    public static TokenDatabase getTokenDatabase() {
        return tokenDatabase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        view();
    }

    private void view() {
        cekALamat();
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navDrawer;
        tokenDatabase = Room.databaseBuilder(getApplicationContext(), TokenDatabase.class, "token-db").build();

        header = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();

        LinearLayout txtLogin = header.findViewById(R.id.txt_itm_login);
        AppCompatButton profile = header.findViewById(R.id.btnProfile);
        uName = header.findViewById(R.id.user_name);
        uEmail = header.findViewById(R.id.user_email);
        userImage = header.findViewById(R.id.user_image);
        log_in_out = menu.findItem(R.id.log);

//      Load Fragment var
        fragmentToLoad = getIntent().getStringExtra("FragmentTag");
        subKategori = getIntent().getStringExtra("subKategori");


//      Token func
        checkToken();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run()   {
                ApiService.getToken(new ApiService.TokenCallback() {
                    @Override
                    public void onTokenReceived(String token) {
                        getUser(token);
                    }

                    @Override
                    public void onTokenError() {

                    }
                });
            }
        });


//        Load Fragment Func
        new Handler().postDelayed(() -> {
            if (fragmentToLoad != null) {
                loadFragment(fragmentToLoad, subKategori);
            } else {
                replaceFragment(new HomeFragment(), "HomeFragment");
            }
        }, 500);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int select = item.getItemId();


                if (select == R.id.home) {
                    replaceFragment(new HomeFragment(), "HomeFragment");
                }
                if (select == R.id.addIklan) {
                    Intent intent = new Intent(Drawer.this, PasangIklan.class);
                    startActivity(intent);
                }
                if (select == R.id.iklanSaya) {
                    Intent intent = new Intent(Drawer.this, IklanSayaFavorite.class);
                    startActivity(intent);
                }
                if (select == R.id.chat) {
                    Toast.makeText(Drawer.this, "404, Page not found", Toast.LENGTH_SHORT).show();
                }
                if (select == R.id.help) {
                    Toast.makeText(Drawer.this, "404, Page not found", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Drawer.this, Login.class);
                startActivity(intent);
                drawerLayout.closeDrawer(navigationView);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProfileFragment(), "ProfileFragment");
                drawerLayout.closeDrawer(navigationView);
            }
        });

//        For DeleteToken
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DeleteToken.class)
                .setInitialDelay(144, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(this).enqueueUniqueWork(
                "DeleteTokenWork", ExistingWorkPolicy.KEEP, workRequest
        );
    }

    @Override
    public void onBackPressed() {
        // Check if the drawer is open, if so, close it
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            // If the current fragment is not HomeFragment, navigate back to HomeFragment
            if (!(getSupportFragmentManager().findFragmentById(R.id.frame_layout) instanceof HomeFragment)) {
                replaceFragment(new HomeFragment(), "HomeFragment");
            } else {
                // If the current fragment is HomeFragment
                if (doubleBackToExitPressedOnce) {
                    // Perform default back press action
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Tab again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000); // 2 seconds timeout for double press
            }
        }
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag);
        fragmentTransaction.commit();
    }

    private void loadFragment(String tag, String subKategori) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("subKategori", subKategori);
        switch (tag) {
            case "AnakFragment":
                fragment = new AnakFragment();
                break;
            case "ElektronikFragment":
                fragment = new ElektronikFragment();
                break;
            case "HobiFragment":
                fragment = new HobiFragment();
                break;
            case "JasaFragment":
                fragment = new JasaFragment();
                break;
            case "KantorFragment":
                fragment = new KantorFragment();
                break;
            case "MobilFragment":
                fragment = new MobilFragment();
                break;
            case "MotorFragment":
                fragment = new MotorFragment();
                break;
            case "PribadiFragment":
                fragment = new PribadiFragment();
                break;
            case "PropertiFragment":
                fragment = new PropertiFragment();
                break;
            case "RumahFragment":
                fragment = new RumahFragment();
                break;
        }

        fragment.setArguments(bundle);
        replaceFragment(fragment, tag);
    }

    public void loadBase64ImageIntoImageView(String base64ImageString) {
        byte[] decodedString = Base64.decode(base64ImageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        userImage.setImageBitmap(decodedByte);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> logout());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dialogLogin(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        Button buttonSubmit = dialogView.findViewById(R.id.login);
        dialog.setCancelable(false);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Drawer.this, Login.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void login() {
        log_in_out.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(Drawer.this, Login.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private void logout() {
        ApiService.clearToken(this);
        Helper.reset(this);
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileAccount", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit().clear();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
    }

    //    API func
    private void getUser(String token) {
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        TokenEntity tokenEntity = getTokenDatabase().tokenDAO().getToken();
                        if (tokenEntity != null) {
                            ApiService.endpoint(token).getProfile().enqueue(new Callback<ProfileModel>() {
                                @Override
                                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        log_in_out.setTitle("Logout");
                                        ProfileModel profileModel = response.body();
                                        ProfileModel.User user = profileModel.getData();
                                        name = user.getName();
                                        email = user.getEmail();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                uName.setText(name);
                                                uEmail.setText(email);
                                                loadBase64ImageIntoImageView(String.valueOf(user.getPict_profile()));
                                            }
                                        });
                                    }
                                    log_in_out.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(@NonNull MenuItem item) {
                                            showLogoutDialog();
                                            return false;
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<ProfileModel> call, Throwable throwable) {
                                    Log.d("ApiService", "Invalid erore" + throwable);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onTokenError() {
                Log.d("error", "onTokenError:" + token);
            }
        });
    }

    private void cekALamat(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);
        Button cancel = dialogView.findViewById(R.id.cancel_button);
        dialog.setCancelable(false);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                replaceFragment(new FormProfile(), "FormProfile");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void checkToken() {
        ApiService.getToken(new ApiService.TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                // Token is valid, continue with the app
                if (token == null || token.isEmpty()) {
                    dialogLogin();
                    login();
                }
            }

            @Override
            public void onTokenError() {
                Toast.makeText(Drawer.this, "Anda belum Login !", Toast.LENGTH_SHORT).show();
                dialogLogin();
                login();
            }
        });
    }
}