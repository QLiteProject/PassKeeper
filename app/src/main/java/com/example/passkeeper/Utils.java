package com.example.passkeeper;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void showMessage(Context context, String text, boolean isQuickMessage) {
        Toast.makeText(context, text, isQuickMessage ? Toast.LENGTH_SHORT : Toast.LENGTH_SHORT).show();
    }
}
