package com.example.passkeeper.Application;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.passkeeper.R;

public class ThemeHelper {
    private final static String THEME_PREF = "Theme";
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_LIGHT = 1;

    public static void loadTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        int theme = prefs.getInt(THEME_PREF, THEME_DEFAULT);
        setThemeActivity(activity, theme);
    }

    public static void changeTheme(Activity activity, int theme) {
        saveTheme(activity, theme);
        activity.recreate();
    }

    public static void saveTheme(Activity activity, int theme) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(THEME_PREF, theme);
        editor.apply();
    }

    private static void setThemeActivity(Activity activity, int theme) {
        switch (theme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.PassKeeperDark);
                break;
            case THEME_LIGHT:
                activity.setTheme(R.style.PassKeeperLight);
                break;
        }
    }
}
