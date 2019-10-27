package com.example.passkeeper.Application;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
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

    public static String getFileText(String uri) {
        File file = new File(uri);
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    text.append(line);
                }
                bufferedReader.close();
                return text.toString();
            } catch (Exception e) {
                return null;
            }
        }else {
            return null;
        }
    }

    public static boolean isFileExists(String uri) {
        File file = new File(uri);
        return file.exists();
    }

    public static File createDir(String uri) {
        File file = new File(uri);
        return file.exists() ? file : (file.mkdirs() ? file : null);
    }

    public static String generateRandomHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }

    public static String getCurrentDateTimeAsString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}