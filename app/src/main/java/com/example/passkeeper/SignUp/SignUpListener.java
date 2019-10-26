package com.example.passkeeper.SignUp;

public interface SignUpListener {
    void onClickNext();
    void onClickBack();
    void onClickCheckboxLicense();
    void onChangedUsername();
    void onChangedPass();
    void onChangedDuplPass();
}
