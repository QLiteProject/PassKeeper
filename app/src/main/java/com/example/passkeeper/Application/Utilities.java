package com.example.passkeeper.Application;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

    public static boolean setFileText(String uri, String data) {
        File file = new File(uri);
        if (file.exists()) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file, false);
                fileOut.write(data.getBytes());
                fileOut.close();
                return true;
            }catch (Exception ignored) {
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean isFileExists(String uri) {
        return new File(uri).exists();
    }

    public static boolean createFile(String uri, String data) {
        File file = new File(uri);
        try {
            if (file.createNewFile()) {
                if (data != null) {
                    FileOutputStream fileOut = new FileOutputStream(file, false);
                    fileOut.write(data.getBytes());
                    fileOut.close();
                }
                return true;
            }else {
                return false;
            }
        }catch (Exception ignored) {
            return false;
        }
    }

    public static File createFolder(String uri) {
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

    public static void doRestart(Context c) {
        try {
            if (c != null) {
                PackageManager pm = c.getPackageManager();
                if (pm != null) {
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        System.exit(0);
                    } else {
                        Log.e(AppConstants.SETTINGS_LOG_TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e(AppConstants.SETTINGS_LOG_TAG, "Was not able to restart application, PM null");
                }
            } else {
                Log.e(AppConstants.SETTINGS_LOG_TAG, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e(AppConstants.SETTINGS_LOG_TAG, "Was not able to restart application");
        }
    }


    public static void onReload(Activity activity) {
        Intent intent = activity.getIntent();
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }
}
