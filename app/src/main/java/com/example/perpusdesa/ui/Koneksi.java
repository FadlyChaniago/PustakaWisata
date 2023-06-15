package com.example.perpusdesa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpusdesa.R;
import com.example.perpusdesa.ui.activity.MainActivity;

public class Koneksi extends AppCompatActivity {

    Button buttonLog, buttonSign;
    boolean userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koneksi);

        buttonLog = findViewById(R.id.log);
        buttonSign = findViewById(R.id.sign);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isLoggedIn = checkLoginStatus();

                if (isLoggedIn) {
                    Intent intent = new Intent(Koneksi.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Koneksi.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Koneksi.this, Registration.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkLoginStatus() {
        return userLoggedIn;
    }
}
