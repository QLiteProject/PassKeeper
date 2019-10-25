package com.example.passkeeper.GenSecretKey;

public interface GenSecretKeyListener {
    void onClickCreateAccount();
    void onClickEndIconAutoGen();
    void onClickEndIconEnterKey();
    void onChangedAutoGen(boolean isChecked);
    void onChangedEnterKey(boolean isChecked);
}
