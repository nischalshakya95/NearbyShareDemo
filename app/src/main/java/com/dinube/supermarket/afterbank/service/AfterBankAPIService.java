package com.dinube.supermarket.afterbank.service;

import com.dinube.supermarket.afterbank.request.PaymentInitiateRequest;
import com.dinube.supermarket.afterbank.response.AccountInformationResponseData;
import com.dinube.supermarket.afterbank.response.PaymentInitiateResponseData;
import com.dinube.supermarket.afterbank.response.PaymentStatusResponseData;
import com.dinube.supermarket.afterbank.response.PaymentSuccessResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AfterBankAPIService {

    @POST("/payment/initiate")
    Call<PaymentInitiateResponseData> paymentInitiate(@Body PaymentInitiateRequest paymentInitiateRequest);

    @GET("/payment/initiate/response")
    Call<PaymentSuccessResponseData> getPaymentSuccessResponse();

    @GET("/payment/status")
    Call<PaymentStatusResponseData> getPaymentStatus(@Query("paymentId") String paymentId);

    @GET("/account/account-information")
    Call<AccountInformationResponseData> getAccountInformation(@Query("accountId") String accountId);
}
