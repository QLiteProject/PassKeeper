package com.example.passkeeper.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserManager;
import com.example.passkeeper.UserAPI.UserModel;

public class SignInManager extends AppCompatActivity {
    private SignInController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);

        UserModel userModel = new UserModel();
        controller = new SignInController(this, userModel);
        SignInView view = new SignInView(getWindow().getDecorView(), controller);
        controller.setView(view);
        UserManager.callback = controller;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        controller.onBack(requestCode, resultCode, data);
    }
}
