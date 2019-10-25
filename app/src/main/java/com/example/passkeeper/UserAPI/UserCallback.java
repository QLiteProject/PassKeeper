package com.example.passkeeper.UserAPI;

public interface UserCallback {
    void onShowFatalError();
    void onSuccessRequest(int requestCode);
}