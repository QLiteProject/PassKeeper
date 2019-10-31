package com.example.passkeeper.Main.CustomDialog;

import android.view.View;

public class CustomDialogController implements CustomDialogListener{
    private final CustomDialogView view;
    private final CustomDialogListener listener;

    public CustomDialogController(View view, CustomDialogListener listener) {
        this.view = new CustomDialogView(view, this);
        this.listener = listener;
    }

    //region get/set
    public View getView() {
        return view.getView();
    }

    public String getTitle() {
        return view.getTitleText();
    }

    public String getLogin() {
        return view.getLoginText();
    }

    public String getPassword() {
        return view.getPasswordText();
    }

    public void setTitle(String text) {
        view.setTitleText(text);
    }

    public void setLogin(String text) {
        view.setLoginText(text);
    }

    public void setPassword(String text) {
        view.setPasswordText(text);
    }
    //endregion
}
