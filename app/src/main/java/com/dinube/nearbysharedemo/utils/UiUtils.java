package com.dinube.nearbysharedemo.utils;

import android.content.Context;
import android.widget.Toast;

public final class UiUtils {

    public static void showToast(Context context, String message, String endpointName) {
        Toast.makeText(context, message + endpointName, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
