package com.example.perpusdesa.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.perpusdesa.R;
import com.example.perpusdesa.ui.activity.BookmarkActivity;
import com.example.perpusdesa.ui.activity.MainActivity;

public class DetailBookActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pdfUrl = intent.getStringExtra("pdf");
        String imgUrl = intent.getStringExtra("img");
        String description = intent.getStringExtra("desc");
        String title = intent.getStringExtra("title");

        ImageView imageView = findViewById(R.id.img_view);

        Glide.with(DetailBookActivity.this)
                .load(imgUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);

        TextView textViewDesc = findViewById(R.id.txt_desc);
        TextView textViewTitle = findViewById(R.id.txt_title);

        textViewTitle.setText(title);

        textViewDesc.setText(description);

        Button btnBaca = findViewById(R.id.btn_baca);
        Button btnSimpan = findViewById(R.id.btn_simpan);
        ImageButton imageButton = findViewById(R.id.back_home);
        sharedPreferences = getSharedPreferences("SavedIds", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean(title, false)){
            btnSimpan.setVisibility(View.GONE);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailHomeActivity.class);
                intent.putExtra("pdf", pdfUrl);
                startActivity(intent);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (isIdSaved(title)) {
//                    Toast.makeText(DetailBookActivity.this, "Buku sudah tersimpan sebelumnya", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                saveId(title);

                Intent intent = new Intent(getApplicationContext(), BookmarkActivity.class);
                intent.putExtra("saved", true);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("pdfUrl", pdfUrl);
                intent.putExtra("desc", description);
                startActivity(intent);
//                Toast.makeText(DetailBookActivity.this, "Menyimpan Buku", Toast.LENGTH_SHORT).show();
            }
        });

    }
//    private boolean isIdSaved(String id) {
//        SharedPreferences sharedPreferences = getSharedPreferences("SavedIds", Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean(id, false);
//    }

    private void saveId(String id) {
        sharedPreferences = getSharedPreferences("SavedIds", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(id, true);
        editor.apply();
    }
}