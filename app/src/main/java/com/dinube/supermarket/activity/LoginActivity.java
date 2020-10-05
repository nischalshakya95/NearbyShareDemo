package com.dinube.supermarket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dinube.supermarket.R;
import com.dinube.supermarket.dinube.model.LoginRequest;
import com.dinube.supermarket.dinube.model.LoginResponse;
import com.dinube.supermarket.dinube.retrofit.DinubeRetrofit;
import com.dinube.supermarket.dinube.service.DinubeAPIService;
import com.dinube.supermarket.utils.UiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;

    TextView phoneNumber;

    TextView password;

    Context context;

    private DinubeAPIService dinubeAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        loginButton = findViewById(R.id.loginButton);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);

        dinubeAPIService = DinubeRetrofit.getDinubeAPIInstance();

        loginButton.setOnClickListener(v -> {
            callAPI();
        });
    }

    private void login() {
        if (phoneNumber.getText().toString().equals("3400000000") && password.getText().toString().equals("no-pass")) {
            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
            startActivity(intent);
            UiUtils.showToast(context, "Login Successful");
        } else {
            UiUtils.showToast(context, "Invalid Credentials");
        }

    }

    private void callAPI() {
        LoginRequest loginRequest = new LoginRequest(phoneNumber.getText().toString(), password.getText().toString(), "2332", "0", "0", "");
        Call<LoginResponse> call = dinubeAPIService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                System.out.println(loginResponse.toString());

                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("authorizationCode", loginResponse.getAuthorizationToken());

                startActivity(intent);
                UiUtils.showToast(context, "Login Successful");
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                UiUtils.showToast(context, t.getMessage());
            }
        });
    }
}