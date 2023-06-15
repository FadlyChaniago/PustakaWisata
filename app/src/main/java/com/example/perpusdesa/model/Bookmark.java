package com.example.perpusdesa.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String url;

    @ColumnInfo(name = "url_image")
    private String url_image;

    private String description;

    public Bookmark(String title, String url, String url_image, String description) {
        this.title = title;
        this.url = url;
        this.url_image = url_image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for url_image field
    public String getUrl_image() {
        return url_image;
    }
}
