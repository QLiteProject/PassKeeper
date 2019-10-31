package com.example.passkeeper.Main.CustomDialog;

import android.view.View;
import android.widget.EditText;

import com.example.passkeeper.R;
import com.google.android.material.textfield.TextInputLayout;

public class CustomDialogView{
    private final CustomDialogListener listener;
    private final View view;
    private TextInputLayout txtInTitle;
    private TextInputLayout txtInLogin;
    private TextInputLayout txtInPassword;
    private EditText editTextTitle;
    private EditText editTextLogin;
    private EditText editTextPassword;

    public CustomDialogView(View view, CustomDialogListener listener) {
        this.listener = listener;
        this.view = view;
        initComponents(view);
    }

    private void initComponents(View view) {
        txtInTitle = (TextInputLayout) view.findViewById(R.id.textIputLayout_cDialogTitle);
        txtInLogin = (TextInputLayout) view.findViewById(R.id.textIputLayout_cDialogLogin);
        txtInPassword = (TextInputLayout) view.findViewById(R.id.textIputLayout_cDialogPassword);
        editTextTitle = (EditText) txtInTitle.getEditText();
        editTextLogin = (EditText) txtInLogin.getEditText();
        editTextPassword = (EditText) txtInPassword.getEditText();
    }

    //region get/set
    public View getView() {
        return view;
    }

    public String getTitleText() {
        return editTextTitle.getText().toString();
    }

    public String getLoginText() {
        return editTextLogin.getText().toString();
    }

    public String getPasswordText() {
        return editTextPassword.getText().toString();
    }

    public void setTitleText(String text) {
        editTextTitle.setText(text);
    }

    public void setLoginText(String text) {
        editTextLogin.setText(text);
    }

    public void setPasswordText(String text) {
        editTextPassword.setText(text);
    }
    //endregion
}
