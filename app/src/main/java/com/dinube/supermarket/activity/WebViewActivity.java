package com.dinube.supermarket.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dinube.supermarket.R;
import com.dinube.supermarket.afterbank.response.PaymentSuccessResponse;
import com.dinube.supermarket.afterbank.response.PaymentSuccessResponseData;
import com.dinube.supermarket.afterbank.retrofit.AfterBankRetrofit;
import com.dinube.supermarket.afterbank.service.AfterBankAPIService;
import com.dinube.supermarket.utils.TempVariables;
import com.dinube.supermarket.utils.UiUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {

    PaymentSuccessResponse paymentSuccessResponse;

    private Context context;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        context = this;

        WebView webView = findViewById(R.id.after_bank_web_view);
        webView.setWebViewClient(new CustomWebView());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(ViewStub.SCROLLBARS_INSIDE_OVERLAY);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(Objects.requireNonNull(getIntent().getStringExtra("url")));
    }

    private PaymentSuccessResponse getPaymentStatus() {
        AfterBankAPIService afterBankAPIService = AfterBankRetrofit.getAfterBankAPIInstance();
        Call<PaymentSuccessResponseData> call = afterBankAPIService.getPaymentSuccessResponse();

        call.enqueue(new Callback<PaymentSuccessResponseData>() {
            @Override
            public void onResponse(Call<PaymentSuccessResponseData> call, Response<PaymentSuccessResponseData> response) {
                assert response.body() != null;
                paymentSuccessResponse = response.body().getT();
                Toast.makeText(context, paymentSuccessResponse.getPaymentId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PaymentSuccessResponseData> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return paymentSuccessResponse;
    }

    class CustomWebView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.contentEquals(TempVariables.AFTER_BANK_REDIRECT_URL)) {
                view.stopLoading();
                view.setVisibility(View.GONE);
//                PaymentSuccessResponse paymentSuccessResponse = getPaymentStatus();
                Intent intent = new Intent(context, PaymentActivity.class);
//                intent.putExtra("paymentId", paymentSuccessResponse.getPaymentId());
                UiUtils.showToast(getApplicationContext(), "Payment Successful");
                startActivity(intent);
            }
        }
    }
}
