package com.frogobox.kasir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    private final static int SPLASH_INTERVAL = 1500;
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // -----------------------------------------------------------------------------------------
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // -----------------------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // -----------------------------------------------------------------------------------------
        setContentView(R.layout.activity_splash_screen);
        // -----------------------------------------------------------------------------------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i); // Menghubungkan activity splashscren ke main activity dengan intent
                this.finish(); // Jeda selesai Splashscreen
            }

            private void finish() {
            }
        }, SPLASH_INTERVAL);
        // -----------------------------------------------------------------------------------------
    }
}