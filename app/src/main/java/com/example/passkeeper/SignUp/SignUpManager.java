package com.example.passkeeper.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.passkeeper.Application.ThemeHelper;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserModel;

public class SignUpManager extends AppCompatActivity {
    private SignUpController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.loadTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        UserModel model = new UserModel();
        controller = new SignUpController(this, model);
        SignUpView view = new SignUpView(getWindow().getDecorView(), controller);
        controller.setView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        controller.onBack(requestCode, resultCode, data);
    }
}
