package com.example.perpusdesa.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.perpusdesa.model.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    LiveData<List<Bookmark>> getAllBookmarks();

    @Insert
    void insertBookmark(Bookmark bookmark);

    @Delete
    void deleteBookmark(Bookmark bookmark);
}
