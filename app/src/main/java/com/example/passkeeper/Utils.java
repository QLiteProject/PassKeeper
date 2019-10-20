package com.example.passkeeper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Utils {
    public static void showMessage(Context context, String text) {
        showMessage(context, text, true);
    }

    public static void showMessage(Context context, String text, boolean isQuickMessage) {
        Toast.makeText(context, text, isQuickMessage ? Toast.LENGTH_SHORT : Toast.LENGTH_SHORT).show();
    }

    public static void clipBoard(Context context, String tag, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(tag, text);
        clipboard.setPrimaryClip(clip);
    }

    public static boolean isFileExists(String uri) {
        File file = new File(uri);
        return file.exists();
    }

    public static boolean createDir(String uri) {
        return new File(uri).mkdir();
    }

    public static String generateRandomHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }
}
