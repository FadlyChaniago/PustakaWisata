package com.example.perpusdesa.network;

import com.example.perpusdesa.model.PepusModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("/books")
    Call<List<PepusModel>> getPerpusList();
}
