package com.example.passkeeper.Application;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Locale;

public class LocaleHelper {
    private static final String LANG_PREF = "Language";

    public static void loadLocale(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String locale = prefs.getString(LANG_PREF, Locales.ENGLISH.getLocale());
        setLocale(activity, Locales.fromString(locale));
    }

    public static void changeLang(Activity activity, Locales locale) {
        if (locale != null && locale != getCurrentLocale(activity)) {
            saveLocale(locale.getLocale(), activity);
        }
    }

    public static void saveLocale(String lang, Activity activity) {
        String langPref = "Language";
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    public static Locales getCurrentLocale(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String locale = prefs.getString(LANG_PREF, Locales.ENGLISH.getLocale());
        return Locales.fromString(locale);
    }

    private static void setLocale(Activity activity, Locales locale) {
        Locale myLocale = new Locale(locale.getLocale());
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        activity.getResources().updateConfiguration(
                config, activity.getResources().getDisplayMetrics()
        );
    }

    public enum Locales {
        RUSSIAN("ru"),
        ENGLISH("eu");

        private String locale;

        Locales (String locale) {
            this.locale = locale;
        }

        public String getLocale() {
            return locale;
        }

        public static Locales fromString(String text) {
            for (Locales l : Locales.values()) {
                if (l.locale.equalsIgnoreCase(text)) {
                    return l;
                }
            }
            return null;
        }
    }
}
