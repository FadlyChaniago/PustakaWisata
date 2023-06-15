package com.example.perpusdesa.dao.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.perpusdesa.dao.BookmarkDao;
import com.example.perpusdesa.dao.database.BookmarkDatabase;
import com.example.perpusdesa.model.Bookmark;

import java.util.List;

public class BookmarkRepository {
    private BookmarkDao bookmarkDao;
    private LiveData<List<Bookmark>> allBookmarks;

    public BookmarkRepository(Application application) {
        BookmarkDatabase database = BookmarkDatabase.getInstance(application);
        bookmarkDao = database.bookmarkDao();
        allBookmarks = bookmarkDao.getAllBookmarks();
    }

    public LiveData<List<Bookmark>> getAllBookmarks() {
        return allBookmarks;
    }

    public void insertBookmark(Bookmark bookmark) {
        new InsertBookmarkAsyncTask(bookmarkDao).execute(bookmark);
    }

    public void deleteBookmark(Bookmark bookmark) {
        new DeleteBookmarkAsyncTask(bookmarkDao).execute(bookmark);
    }

    private static class InsertBookmarkAsyncTask extends AsyncTask<Bookmark, Void, Void> {
        private BookmarkDao bookmarkDao;

        private InsertBookmarkAsyncTask(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }

        @Override
        protected Void doInBackground(Bookmark... bookmarks) {
            bookmarkDao.insertBookmark(bookmarks[0]);
            return null;
        }
    }

    private static class DeleteBookmarkAsyncTask extends AsyncTask<Bookmark, Void, Void> {
        private BookmarkDao bookmarkDao;

        private DeleteBookmarkAsyncTask(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }

        @Override
        protected Void doInBackground(Bookmark... bookmarks) {
            bookmarkDao.deleteBookmark(bookmarks[0]);
            return null;
        }
    }
}
