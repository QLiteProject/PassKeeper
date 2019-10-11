package com.example.passkeeper.SignIn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.passkeeper.R;
import com.example.passkeeper.SignUp.SignUpController;
import com.example.passkeeper.Utils;

public class SignInController extends AppCompatActivity {
    private final int REQUEST_CODE_SIGN_UP = 1;
    private SignInView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);

        view = new SignInView(getWindow().getDecorView(), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onBack(requestCode, resultCode, data);
    }

    //region events
    protected void onClickSignIn() {

    }

    protected void onClickSignUp() {
        Intent intentSingUp = new Intent(this, SignUpController.class);
        startActivityForResult(intentSingUp, REQUEST_CODE_SIGN_UP);
    }

    protected void onBack(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SIGN_UP && data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    String username = data.getStringExtra(getString(R.string.username));
                    String pass = data.getStringExtra(getString(R.string.password));
                    view.setTextUsername(username);
                    view.setTextPass(pass);
                    Utils.showMessage(this, getString(R.string.auth_login_process) + "[Username=" + username + ", Password=" + pass + "]", false);
                    break;
            }
        }
    }
    //endregion
}
