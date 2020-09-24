package com.dinube.supermarket.afterbank.retrofit;

import com.dinube.supermarket.afterbank.service.AfterBankAPIService;
import com.dinube.supermarket.utils.TempVariables;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AfterBankRetrofit {

    private static final String BASE_URL = TempVariables.AFTER_BANK_BASE_URL;

    public static AfterBankAPIService getAfterBankAPIInstance() {
        return getInstance().create(AfterBankAPIService.class);
    }

    public static Retrofit getInstance() {
        return new Retrofit.Builder().client(getOKHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private static OkHttpClient getOKHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
