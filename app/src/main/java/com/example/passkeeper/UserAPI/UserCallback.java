package com.example.passkeeper.UserAPI;

public interface UserCallback {
    void onShowFatalError();
    void onSuccessRequest(UserManager.UserEvent userEvent);
    void onFailureRequest(UserManager.UserEvent userEvent);
}