package com.example.passkeeper.Application;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.passkeeper.R;

public class ThemeHelper {
    private final static String THEME_PREF = "Theme";
    private static Themes SWITCHES_THEME = null;

    public static void loadTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        int idTheme = prefs.getInt(THEME_PREF, Themes.DARK.getTheme());
        Themes theme = Themes.fromInt(idTheme);
        setThemeActivity(activity, getLoadTheme(theme));
    }

    private static Themes getLoadTheme(Themes theme) {
        if (SWITCHES_THEME != null) {
            return SWITCHES_THEME;
        }else {
            return theme;
        }
    }

    public static void changeTheme(Activity activity) {
        if (SWITCHES_THEME != null && SWITCHES_THEME != getCurrentTheme(activity)) {
            saveTheme(activity, SWITCHES_THEME);
            SWITCHES_THEME = null;
        }
    }

    public static void changeTheme(Activity activity, Themes theme) {
        if (theme != null && theme != getCurrentTheme(activity)) {
            saveTheme(activity, theme);
            SWITCHES_THEME = null;
        }
    }

    public static void switchTheme(Activity activity, Themes theme) {
        if (SWITCHES_THEME != theme) {
            SWITCHES_THEME = theme;
            activity.recreate();
        }
    }

    public static void saveTheme(Activity activity, Themes theme) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(THEME_PREF, theme.getTheme());
        editor.apply();
    }

    public static Themes getCurrentTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        int idTheme =  prefs.getInt(THEME_PREF, Themes.DARK.getTheme());
        return Themes.fromInt(idTheme);
    }

    public static void resetSwitchesTheme() {
        SWITCHES_THEME = null;
    }

    private static void setThemeActivity(Activity activity, Themes theme) {
        switch (theme) {
            default:
            case DARK:
                activity.setTheme(R.style.PassKeeperDark);
                break;
            case LIGHT:
                activity.setTheme(R.style.PassKeeperLight);
                break;
        }
    }

    public enum Themes {
        DARK(0),
        LIGHT(1);

        private int theme;

        Themes (int theme) {
            this.theme = theme;
        }

        public int getTheme() {
            return theme;
        }

        public static Themes fromInt(int id) {
            for (Themes t : Themes.values()) {
                if (t.theme == id) {
                    return t;
                }
            }
            return null;
        }
    }
}
