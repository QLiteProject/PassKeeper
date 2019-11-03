package com.example.passkeeper.GenSecretKey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.passkeeper.Application.ThemeHelper;
import com.example.passkeeper.R;


public class GenSecretKeyManager extends AppCompatActivity {
    private GenSecretKeyController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.loadTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_secret_key_form);

        controller = new GenSecretKeyController(this);
        GenSecretKeyView view = new GenSecretKeyView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.generateRandomSecretKey();
        controller.generateWelcomeText();
    }
}
