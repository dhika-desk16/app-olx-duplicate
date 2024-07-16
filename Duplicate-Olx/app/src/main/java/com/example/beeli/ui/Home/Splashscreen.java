package com.example.beeli.ui.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.beeli.R;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_screen);
        videoView.setVideoURI(videoUri);

        videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(Splashscreen.this, Drawer.class));
            finish();
        });

        videoView.setOnErrorListener((mp, what, extra) -> {
            startActivity(new Intent(Splashscreen.this, Drawer.class));
            finish();
            return true;
        });

        videoView.start();
    }
}
