package com.example.passkeeper.Application;

import android.os.Environment;

import java.io.File;

public class AppConstants {
    public static final String URL_SERVER = "http://passkeeper.duckdns.org";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SECRET_KEY = "secret_key";
    public static final String USER_DATA = "user_data";
    public static final String BASE = "base";
    public static final String BASE_NAME = "name";
    public static final String BASE_RECORDS = "records";
    public static final String BASE_CREATE_DATE = "create_date";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String BASE_TITLE = "title";
    public static final String BASE_LOGIN = "login";
    public static final String BASE_PASSWORD = "password";
    public static final String APP_FOLDER =  ROOT_PATH + File.separator + ".PassKeeper";
    public static final String AUTO_GEN_TAG = "auto_gen";
    public static final String ENTER_KEY_TAG = "enter_key";
    public static final String SETTINGS_LOG_TAG = "settings";
    public static final String SETTINGS_THEME = "settings_theme";
    public static final String SETTINGS_LANG = "settings_lang";
    public static final int MIN_LENGTH_FIELDS = 4;
    public static final int SECRET_BYTE = 8;

    public static final int REQUEST_CODE_SIGN_UP = 1;
    public static final int REQUEST_CODE_GEN_SECRET_KEY = 2;
    public static final int REQUEST_CODE_ENTER_SECRET_KEY = 3;
    public static final int REQUEST_CODE_SETTINGS = 4;
}
