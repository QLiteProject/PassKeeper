package com.example.passkeeper.Application;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class LocaleHelper {
    private static final String DEFAULT_LANG = "eu";

    public static void loadLocale(Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, DEFAULT_LANG);
        changeLang(language, context);
    }

    public static void changeLang(String lang, Context context) {
        Locale myLocale = new Locale(lang);
        saveLocale(lang, context);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void saveLocale(String lang, Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }
}
