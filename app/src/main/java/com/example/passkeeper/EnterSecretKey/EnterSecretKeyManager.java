package com.example.passkeeper.EnterSecretKey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.passkeeper.GenSecretKey.GenSecretKeyView;
import com.example.passkeeper.R;

public class EnterSecretKeyManager extends AppCompatActivity {
    EnterSecretKeyController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_secret_key_form);

        controller = new EnterSecretKeyController(this);
        EnterSecretKeyView view = new EnterSecretKeyView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.generateWelcomeText();
    }
}
