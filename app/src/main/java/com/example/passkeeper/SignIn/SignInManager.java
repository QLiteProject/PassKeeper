package com.example.passkeeper.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserManager;

public class SignInManager extends AppCompatActivity {
    private SignInController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);

        controller = new SignInController(this);
        SignInView view = new SignInView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.createAppFolder();
        UserManager.callback = controller;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        controller.onBack(requestCode, resultCode, data);
    }

    private void initTheme() {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                setTheme(R.style.PassKeeperDark);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                setTheme(R.style.PassKeeperLight);
                break;
        }
    }

}
