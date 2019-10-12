package com.example.passkeeper.SignIn;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.passkeeper.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignInView {
    private final SignInController controller;
    private Button btn_signUp;
    private Button btn_signIn;
    private TextInputLayout txtIn_username;
    private TextInputLayout txtIn_pass;
    private EditText editText_username;
    private EditText editText_pass;

    public SignInView(View view, SignInController controller) {
        this.controller = controller;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        btn_signUp = (Button) view.findViewById(R.id.button_authSignUp);
        btn_signIn = (Button) view.findViewById(R.id.button_authSignIn);
        txtIn_username = (TextInputLayout) view.findViewById(R.id.textInputLayout_authUsername);
        txtIn_pass = (TextInputLayout) view.findViewById(R.id.textInputLayout_authPassword);
        editText_username = txtIn_username.getEditText();
        editText_pass = txtIn_pass.getEditText();
    }

    private void initEvents() {
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onClickSignUp();
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onClickSignIn();
            }
        });
    }

    //region get/set
    protected void setTextUsername(String text) {
        editText_username.setText(text);
    }

    protected void setTextPass(String text) {
        editText_pass.setText(text);
    }
    //endregion
}