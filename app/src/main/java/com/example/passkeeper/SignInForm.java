package com.example.passkeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInForm extends AppCompatActivity {

    private Button button_SignUp;
    private Button button_SignIn;
    private SignInController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);
        initComponents();
        initClickListener();
        controller = new SignInController(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        controller.onBack(requestCode, resultCode, data);
    }

    public void initComponents() {
        button_SignUp = findViewById(R.id.button_signUp);
        button_SignIn = findViewById(R.id.button_signIn);
    }

    public void initClickListener() {
        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onClickSignUp();
            }
        });
        button_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onClickSignIn();
            }
        });
    }
}
