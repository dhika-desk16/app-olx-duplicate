package com.example.beeli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.beeli.ui.Home.Drawer;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
    private static AlertDialog dialog;

    public static String getCurrencyFormat(int s){
        try{
            NumberFormat nf = NumberFormat.getInstance(new Locale("id","ID"));
            return nf.format(s);
        }catch (Exception e){
            return "0";
        }
    }

    public static void progressDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public static void disableDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static Bitmap decodeBase64Image(String base64Image) {
        if (base64Image == null) {
            return null;
        }
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static void reset(Context context){
        Intent intent = new Intent(context, Drawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
