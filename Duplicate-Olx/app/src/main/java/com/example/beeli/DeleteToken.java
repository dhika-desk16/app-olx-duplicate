package com.example.beeli;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.beeli.roomDB.TokenDatabase;
import com.example.beeli.ui.Home.Drawer;

public class DeleteToken extends Worker {
    private TokenDatabase db;
    public DeleteToken(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = Drawer.getTokenDatabase();
    }

    @NonNull
    @Override
    public Result doWork() {
        db.tokenDAO().deleteAllTokens();
        Log.e("DeleteToken", "doWork: Token Deleted" );
        return Result.success();
    }
}
