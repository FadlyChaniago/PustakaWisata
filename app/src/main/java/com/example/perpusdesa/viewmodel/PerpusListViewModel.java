package com.example.perpusdesa.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.perpusdesa.model.PepusModel;
import com.example.perpusdesa.network.APIService;
import com.example.perpusdesa.network.Retro;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerpusListViewModel extends ViewModel {

    private MutableLiveData<List<PepusModel>> perpusList;

    public PerpusListViewModel(){
        perpusList = new MutableLiveData<>();
    }

    public MutableLiveData<List<PepusModel>> getPerpusListObserver() {
        return perpusList;

    }

    public void makeApiCall() {
        APIService apiService = Retro.getRetroClient().create(APIService.class);
        Call<List<PepusModel>> call = apiService.getPerpusList();
        call.enqueue(new Callback<List<PepusModel>>() {
            @Override
            public void onResponse(Call<List<PepusModel>> call, Response<List<PepusModel>> response) {
                List<PepusModel> perpusModels = response.body();
                if (perpusModels != null) {
                    for (PepusModel perpusModel : perpusModels) {
                        // Menggunakan UUID untuk menghasilkan ID unik
                        String id = UUID.randomUUID().toString();
                        perpusModel.setId(id);
                    }
                }
                perpusList.postValue(perpusModels);
            }

            @Override
            public void onFailure(Call<List<PepusModel>> call, Throwable t) {
                perpusList.postValue(null);
            }
        });
    }
}
