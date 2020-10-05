package com.dinube.supermarket.dinube.service;

import com.dinube.supermarket.dinube.model.GenerateUskRequest;
import com.dinube.supermarket.dinube.model.GenerateUskResponse;
import com.dinube.supermarket.dinube.model.LoginRequest;
import com.dinube.supermarket.dinube.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DinubeAPIService {

    @POST("DnbUser/userLogin/03")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("DnbTransactions/requestUsk/02")
    Call<GenerateUskResponse> generateUsk(@Body GenerateUskRequest generateUskRequest);
}
