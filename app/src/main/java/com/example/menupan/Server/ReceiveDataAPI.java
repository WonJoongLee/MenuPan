package com.example.menupan.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReceiveDataAPI {
    @GET("/")
    Call<List<ReceiveData>> get_posts();
}
