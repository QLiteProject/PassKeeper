package com.example.passkeeper.Application;

import android.os.Environment;

import java.io.File;

public class AppConstants {
    public static final String URL_SERVER = "http://passkeeper.duckdns.org";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SECRET_KEY = "secret_key";
    public static final String USER_DATA = "user_data";
    public static final String USER_BASE = "user_base";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String APP_FOLDER =  ROOT_PATH + File.separator + ".PassKeeper";
    public static final String AUTO_GEN_TAG = "auto_gen";
    public static final String ENTER_KEY_TAG = "enter_key";
    public static final int MIN_LENGTH_FIELDS = 4;
    public static final int SECRET_BYTE = 8;

    public static final int REQUEST_CODE_SIGN_UP = 1;
    public static final int REQUEST_CODE_GEN_SECRET_KEY = 2;
    public static final int REQUEST_CODE_ENTER_SECRET_KEY = 3;
}
