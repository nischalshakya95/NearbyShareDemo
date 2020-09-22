package com.dinube.nearbysharedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.nearby.NearbyAdvertise;
import com.dinube.nearbysharedemo.nearby.NearbyConnectionLifeCycleCallback;
import com.dinube.nearbysharedemo.nearby.NearbyDiscover;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class MainActivity extends AppCompatActivity {

    private Context context;

    private String endpointName;

    private RecyclerView recyclerView;

    Button sendMessageButton;

    private TextView textView;

    private ImageView imageView;

    Button generateQRCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        endpointName = Build.MODEL;
        recyclerView = findViewById(R.id.endpointsRecyclerView);
        sendMessageButton = findViewById(R.id.sendMessage);
        textView = findViewById(R.id.message);
        imageView = findViewById(R.id.qrImageView);
        generateQRCodeButton = findViewById(R.id.generateQRButton);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch advertiseSwitch = findViewById(R.id.advertiseSwitch);
        advertiseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                NearbyAdvertise.startAdvertising(context, endpointName, recyclerView);
            } else {
                NearbyAdvertise.stopAdvertising(context, endpointName);
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch discoverSwitch = findViewById(R.id.discoverSwitch);
        discoverSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                NearbyDiscover.startDiscovering(context, endpointName, recyclerView);
            } else {
                NearbyDiscover.stopDiscovering(context, endpointName);
            }
        });

        sendMessageButton.setOnClickListener(click -> {
            String message = textView.getText().toString();
            NearbyConnectionLifeCycleCallback.sendMessage(message, context);
        });

        generateQRCodeButton.setOnClickListener(click -> {
            renderQRCode();
        });
    }

    private void renderQRCode() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(textView.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);

            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}