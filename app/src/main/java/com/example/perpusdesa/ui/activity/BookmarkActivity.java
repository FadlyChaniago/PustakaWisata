package com.example.perpusdesa.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpusdesa.R;
import com.example.perpusdesa.adapter.BookmarkListAdapter;
import com.example.perpusdesa.model.Bookmark;
import com.example.perpusdesa.viewmodel.BookmarkViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private BookmarkViewModel bookmarkViewModel;
    private BookmarkListAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        bottomNavigationView = findViewById(R.id.book);

        bottomNavigationView.setSelectedItemId(R.id.bookmark);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bookmark) {
                return true;
            } else if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookmarkListAdapter(this);
        recyclerView.setAdapter(adapter);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        bookmarkViewModel.getAllBookmarks().observe(this, new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {
                adapter.setBookmarks(bookmarks);
            }
        });

        Intent intent = getIntent();
        boolean save = intent.getBooleanExtra("saved", false);
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String imgUrl = intent.getStringExtra("imgUrl");
        String pdfUrl = intent.getStringExtra("pdfUrl");
        String desc = intent.getStringExtra("desc");


        if (save && title != null && !title.isEmpty() &&
                id != null && !id.isEmpty() &&
                imgUrl != null && !imgUrl.isEmpty() &&
                pdfUrl != null && !pdfUrl.isEmpty() &&
                desc != null && !desc.isEmpty()) {
            Bookmark newBookmark = new Bookmark(title, imgUrl, pdfUrl, desc);
            bookmarkViewModel.insertBookmark(newBookmark);
        }
        adapter.setOnBookmarkClickListener(new BookmarkListAdapter.OnBookmarkClickListener() {
            @Override
            public void onDeleteBookmark(Bookmark bookmark) {
                // Logika delete bookmark di sini
                bookmarkViewModel.deleteBookmark(bookmark);
            }
        });

    }
}
