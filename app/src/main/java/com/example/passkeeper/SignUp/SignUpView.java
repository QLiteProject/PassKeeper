package com.example.passkeeper.SignUp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.passkeeper.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpView {
    private final SignUpController controller;
    private Button btn_createAccount;
    private Button btn_Back;
    private CheckBox chBox_license;
    private TextInputLayout txtIn_username;
    private TextInputLayout txtIn_pass;
    private TextInputLayout txtIn_duplPass;
    private EditText editText_username;
    private EditText editText_pass;
    private EditText editText_duplPass;

    public SignUpView(View view, SignUpController controller) {
        this.controller = controller;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        btn_createAccount = (Button) view.findViewById(R.id.button_regCreateAccount);
        btn_Back = (Button) view.findViewById(R.id.button_regBack);
        chBox_license = (CheckBox) view.findViewById(R.id.checkBox_regLicense);
        txtIn_username = (TextInputLayout) view.findViewById(R.id.textInputLayout_regUsername);
        txtIn_pass = (TextInputLayout) view.findViewById(R.id.textInputLayout_regPassword);
        txtIn_duplPass = (TextInputLayout) view.findViewById(R.id.textInputLayout_regDuplicatePassword);
        editText_username = txtIn_username.getEditText();
        editText_pass = txtIn_pass.getEditText();
        editText_duplPass = txtIn_duplPass.getEditText();
    }

    private void initEvents() {
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onClickCreateAccount();
            }
        });
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onClickBack();
            }
        });
        chBox_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onClickCheckboxLicense();
            }
        });
        editText_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.onChangedUsername();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.onChangedPass();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_duplPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.onChangedDuplPass();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //region get/set
    protected void setEnabledCheckboxLicense(boolean isEnabled) {
        chBox_license.setEnabled(isEnabled);
    }

    protected void setCheckedLicense(boolean isChecked) {
        chBox_license.setChecked(isChecked);
    }

    protected void setErrMsgUsername(String message) {
        txtIn_username.setError(message);
    }

    protected void setErrMsgPass(String message) {
        txtIn_pass.setError(message);
    }

    protected void setErrMsgDuplPass(String message) {
        txtIn_duplPass.setError(message);
    }

    protected boolean getCheckedLicense() {
        return chBox_license.isChecked();
    }

    protected String getTextUsername() {
        return editText_username.getText().toString();
    }

    protected String getTextPass() {
        return editText_pass.getText().toString();
    }

    protected String getTextDuplPass() {
        return editText_duplPass.getText().toString();
    }
    //endregion
}
