package com.example.passkeeper.SignUp;

import android.content.Intent;

public interface SignUpListener {
    void onClickNext();
    void onClickBack();
    void onClickCheckboxLicense();
    void onChangedUsername();
    void onChangedPass();
    void onChangedDuplPass();
    void onBack(int requestCode, int resultCode, Intent data);
}
