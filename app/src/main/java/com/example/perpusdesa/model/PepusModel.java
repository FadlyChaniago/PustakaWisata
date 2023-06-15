package com.example.perpusdesa.model;

import java.util.UUID;

public class PepusModel {
    private String id;
    private String url;
    private String url_image;
    private String desciption;
    private String title;
    private String category;
    private String author;

    public PepusModel(String title,String url, String image, String id,
                      String desciption,String category, String author) {
        this.title = title;
        this.url = url;
        this.url_image = image;
        this.desciption = desciption;
        this.id = UUID.randomUUID().toString();
        this.category = category;
        this.author = author;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPdfUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getImage() {
        return url_image;
    }

    public void setImage(String image) {
        this.url_image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesciption() { return desciption; }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
