package com.example.passkeeper.Main.CustomDialog;

import android.view.View;

import com.example.passkeeper.Application.Utilities;

import io.bloco.faker.Faker;

public class CustomDialogController implements CustomDialogListener{
    private final Faker faker = new Faker();
    private final CustomDialogView view;

    public CustomDialogController(View view) {
        this.view = new CustomDialogView(view, this);
    }

    //region events
    @Override
    public void onClickGenerateLogin() {
        String username = faker.internet.userName();
        view.setLoginText(username);
    }

    @Override
    public void onClickGeneratePassword() {
        String password = Utilities.getRandomPassword(8, 16);
        view.setPasswordText(password);
    }
    //endregion

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
