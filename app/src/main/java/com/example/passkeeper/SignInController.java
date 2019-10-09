package com.example.passkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public class SignInController {
    private final SignInForm parentController;
    private final Context parentContext;

    public SignInController(SignInForm parentController) {
        this.parentController = parentController;
        this.parentContext = parentController.getApplicationContext();
    }

    public void onClickSignIn() {

    }

    public void onClickSignUp() {
        Intent intentSingUp = new Intent(parentController, SignUpForm.class);
        parentController.startActivityForResult(intentSingUp, 0);
    }

    public void onBack(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Activity.RESULT_OK && data != null) {
            switch (resultCode) {
                case 0:
                    String username = data.getStringExtra("username");
                    String password = data.getStringExtra("password");
                    String secretKey = data.getStringExtra("secretKey");
                    Utils.showMessage(parentContext, parentContext.getString(R.string.auth_login_process), true);
                    break;
            }
        }
    }
}
