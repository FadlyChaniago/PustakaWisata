package com.example.perpusdesa.ui.screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpusdesa.R;
import com.example.perpusdesa.ui.Login;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferences = getSharedPreferences("slide", MODE_PRIVATE);
        editor = preferences.edit();

        boolean slide = preferences.getBoolean("slide", false);

        if (slide) {
            intent = new Intent(SplashScreen.this, Login.class);
        } else {
            intent = new Intent(SplashScreen.this, SlideActivity.class);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}