package com.example.passkeeper.SignIn;

import android.content.Intent;

public interface SignInListener {
    void onClickSignIn();
    void onClickSignUp();
    void onBack(int requestCode, int resultCode, Intent data);
}
