package com.example.perpusdesa.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.perpusdesa.dao.repository.BookmarkRepository;
import com.example.perpusdesa.model.Bookmark;

import java.util.List;

public class BookmarkViewModel extends AndroidViewModel {
    private BookmarkRepository bookmarkRepository;
    private LiveData<List<Bookmark>> allBookmarks;

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        bookmarkRepository = new BookmarkRepository(application);
        allBookmarks = bookmarkRepository.getAllBookmarks();
    }

    public LiveData<List<Bookmark>> getAllBookmarks() {
        return allBookmarks;
    }

    public void insertBookmark(Bookmark bookmark) {
        bookmarkRepository.insertBookmark(bookmark);
    }

    public void deleteBookmark(Bookmark bookmark) {
        bookmarkRepository.deleteBookmark(bookmark);
    }
}
