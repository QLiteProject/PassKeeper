package com.example.passkeeper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void showMessage(Context context, String text, boolean isQuickMessage) {
        Toast.makeText(context, text, isQuickMessage ? Toast.LENGTH_SHORT : Toast.LENGTH_SHORT).show();
    }

    public static void clipBoard(Context context, String tag, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(tag, text);
        clipboard.setPrimaryClip(clip);
    }
}
