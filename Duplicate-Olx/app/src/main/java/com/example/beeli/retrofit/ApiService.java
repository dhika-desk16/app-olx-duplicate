package com.example.beeli.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.beeli.roomDB.TokenDAO;
import com.example.beeli.roomDB.TokenEntity;
import com.example.beeli.ui.Home.Drawer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {
//    Ganti URL di sini dan file domain dalam package xml
    private static String Base_URL = "http://192.168.1.20/api/";
    private static Retrofit retrofit;
    private static final String PREF_NAME = "app_prefs";
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    private static TokenDAO tokenDAO = Drawer.getTokenDatabase().tokenDAO();

    private static void addLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Set desired log level
        okHttpClientBuilder.addInterceptor(logging);
    }



    public static ApiEndpoint endpoint() {
        addLoggingInterceptor(); // Ensure logging interceptor is added
        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiEndpoint.class);
    }

    //    B
    public static ApiEndpoint endpoint(String token) {
        if (token != null) {
            okHttpClientBuilder.interceptors().clear();
            okHttpClientBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            });
        }
        addLoggingInterceptor();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ApiEndpoint.class);
    }


    //    Token func
    public interface TokenCallback {
        void onTokenReceived(String token);
        void onTokenError();
    }
    public static void setToken(String token) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                TokenEntity tokenEntity = new TokenEntity(token);
                tokenDAO.insertToken(tokenEntity);
                return null;
            }
        }.execute();
    }
    public static void getToken(TokenCallback callback) {
        new AsyncTask<Void, Void, TokenEntity>() {
            @Override
            protected TokenEntity doInBackground(Void... voids) {
                return tokenDAO.getToken();
            }

            @Override
            protected void onPostExecute(TokenEntity tokenEntity) {
                if (tokenEntity != null) {
                    callback.onTokenReceived(tokenEntity.getToken());
                } else {
                    callback.onTokenError();
                }
            }
        }.execute();
    }
    public static void clearToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();

        // Clear token from Room Database
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                tokenDAO.deleteAllTokens();
                return null;
            }
        }.execute();
    }
}