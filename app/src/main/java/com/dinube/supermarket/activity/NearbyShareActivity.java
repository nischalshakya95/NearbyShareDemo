package com.dinube.supermarket.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dinube.supermarket.R;
import com.dinube.supermarket.afterbank.exception.AfterBankException;
import com.dinube.supermarket.afterbank.request.PaymentInitiateRequest;
import com.dinube.supermarket.afterbank.response.AccountInformationResponse;
import com.dinube.supermarket.afterbank.response.AccountInformationResponseData;
import com.dinube.supermarket.afterbank.response.PaymentInitiateResponse;
import com.dinube.supermarket.afterbank.response.PaymentInitiateResponseData;
import com.dinube.supermarket.afterbank.retrofit.AfterBankRetrofit;
import com.dinube.supermarket.afterbank.service.AfterBankAPIService;
import com.dinube.supermarket.dinube.model.GenerateUskRequest;
import com.dinube.supermarket.dinube.model.GenerateUskResponse;
import com.dinube.supermarket.dinube.retrofit.DinubeRetrofit;
import com.dinube.supermarket.dinube.service.DinubeAPIService;
import com.dinube.supermarket.nearbyshare.NearbyAdvertise;
import com.dinube.supermarket.utils.TempVariables;
import com.dinube.supermarket.utils.UiUtils;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class NearbyShareActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch advertiseSwitch;

    private String endpointName;

    private Context context;

    private TextView textView;

    private IntentIntegrator qrScan;

    private TextView ibanNumberView;

    private TextView balanceView;

    private TextView uskView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_share);

        context = this;
        endpointName = Build.MODEL;

        advertiseSwitch = findViewById(R.id.advertiseSwitch);
        textView = findViewById(R.id.amountPlainText);
        ibanNumberView = findViewById(R.id.ibanNumber);
        balanceView = findViewById(R.id.balanceView);
        uskView = findViewById(R.id.uskView);

        Button initiatePayment = findViewById(R.id.paymentInitiate);
        Button readQRButton = findViewById(R.id.scanQR);
        Button checkBalanceButton = findViewById(R.id.checkBalanceButton);
        Button generateUskButton = findViewById(R.id.generateUSKButton);

        qrScan = new IntentIntegrator(this);

        advertiseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                NearbyAdvertise.startAdvertising(context, endpointName, new NearbyPayloadCallback(), uskView.getText().toString());
            } else {
                NearbyAdvertise.stopAdvertising(context, endpointName);
            }
        });

        initiatePayment.setOnClickListener((click) -> {
            initiatePayment();
        });

        readQRButton.setOnClickListener(click -> {
            qrScan.initiateScan();
        });

        checkBalanceButton.setOnClickListener(click -> {
            checkBalance();
        });

        generateUskButton.setOnClickListener(click -> {
            generateUsk();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                textView.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void generateUsk() {
        DinubeAPIService dinubeAPIService = DinubeRetrofit.getDinubeAPIInstance();
        GenerateUskRequest generateUskRequest = new GenerateUskRequest("34000000000", getIntent().getStringExtra("authorizationToken"), 0, true);
        Call<GenerateUskResponse> call = dinubeAPIService.generateUsk(generateUskRequest);

        call.enqueue(new Callback<GenerateUskResponse>() {
            @Override
            public void onResponse(Call<GenerateUskResponse> call, Response<GenerateUskResponse> response) {
                assert response.body() != null;
                GenerateUskResponse generateUskResponse = response.body();
                uskView.setText(generateUskResponse.getNfcUsk());
            }

            @Override
            public void onFailure(Call<GenerateUskResponse> call, Throwable t) {
                UiUtils.showToast(context, t.getMessage());
            }
        });
    }

    private void checkBalance() {
        AfterBankAPIService afterBankAPIService = AfterBankRetrofit.getAfterBankAPIInstance();
        Call<AccountInformationResponseData> call = afterBankAPIService.getAccountInformation(TempVariables.SOURCE_IBAN_NUMBER);

        call.enqueue(new Callback<AccountInformationResponseData>() {
            @Override
            public void onResponse(Call<AccountInformationResponseData> call, Response<AccountInformationResponseData> response) {
                assert response.body() != null;
                AccountInformationResponse accountInformationResponse = response.body().getT();

                balanceView.setText(String.valueOf(accountInformationResponse.getBalance()));
                ibanNumberView.setText(String.valueOf(accountInformationResponse.getIban()));
            }

            @Override
            public void onFailure(Call<AccountInformationResponseData> call, Throwable t) {
                UiUtils.showToast(context, t.getMessage());
            }
        });
    }


    private void initiatePayment() {
        PaymentInitiateRequest paymentInitiateRequest = new PaymentInitiateRequest(Integer.parseInt(textView.getText().toString()));
        AfterBankAPIService afterBankAPIService = AfterBankRetrofit.getAfterBankAPIInstance();
        Call<PaymentInitiateResponseData> call = afterBankAPIService.paymentInitiate(paymentInitiateRequest);

        call.enqueue(new Callback<PaymentInitiateResponseData>() {

            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<PaymentInitiateResponseData> call, Response<PaymentInitiateResponseData> response) {
                assert response.body() != null;
                if (response.code() == 400) {
                    Converter<ResponseBody, AfterBankException> converter = AfterBankRetrofit.getInstance().responseBodyConverter(AfterBankException.class, new Annotation[0]);
                    try {

                        assert response.errorBody() != null;
                        AfterBankException afterBankException = converter.convert(response.errorBody());
                        assert afterBankException != null;

                        Toast.makeText(context, afterBankException.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    PaymentInitiateResponse paymentInitiateResponse = response.body().getT();
                    Toast.makeText(context, paymentInitiateResponse.getFollow(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", paymentInitiateResponse.getFollow());
                    startActivity(intent);
                    NearbyAdvertise.stopAdvertising(context, endpointName);
                }
            }

            @Override
            public void onFailure(Call<PaymentInitiateResponseData> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class NearbyPayloadCallback extends PayloadCallback {

        @Override
        public void onPayloadReceived(@NonNull String s, @NonNull Payload payload) {
            byte[] receivedBytes = payload.asBytes();
            String message = new String(receivedBytes);
            UiUtils.showToast(context, "Total purchase price " + message);
            textView.setText(message);
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {

        }
    }
}