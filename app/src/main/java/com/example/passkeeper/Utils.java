package com.example.passkeeper;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void showMessage(Context context, String text, boolean isQuickMassege) {
        Toast.makeText(context, text, isQuickMassege ? Toast.LENGTH_SHORT : Toast.LENGTH_SHORT).show();
    }
}
