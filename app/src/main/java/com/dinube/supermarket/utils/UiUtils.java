package com.dinube.supermarket.utils;

import android.content.Context;
import android.widget.Toast;

public class UiUtils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
