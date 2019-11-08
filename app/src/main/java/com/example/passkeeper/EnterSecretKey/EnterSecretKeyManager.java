package com.example.passkeeper.EnterSecretKey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.passkeeper.Application.ThemeHelper;
import com.example.passkeeper.R;

public class EnterSecretKeyManager extends AppCompatActivity {
    private EnterSecretKeyController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.loadTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_secret_key_form);

        controller = new EnterSecretKeyController(this);
        EnterSecretKeyView view = new EnterSecretKeyView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.generateWelcomeText();
    }
}
