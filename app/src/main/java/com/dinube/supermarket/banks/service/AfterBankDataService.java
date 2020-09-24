package com.dinube.supermarket.banks.service;

import com.dinube.supermarket.banks.model.data.BankData;
import com.dinube.supermarket.banks.model.data.ConsentResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AfterBankDataService {

    @GET("/listOfSupportedBanks")
    Call<BankData> findAllBanks();

    @GET("/consent/get")
    Call<ConsentResponseData> getConsent();
}
