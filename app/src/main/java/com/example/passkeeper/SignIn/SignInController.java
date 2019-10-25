package com.example.passkeeper.SignIn;

import android.app.Activity;
import android.content.Intent;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserCallback;
import com.example.passkeeper.SignUp.SignUpManager;
import com.example.passkeeper.UserAPI.UserManager;
import com.example.passkeeper.UserAPI.UserModel;
import com.example.passkeeper.Application.Utilities;

public class SignInController implements SignInListener, UserCallback {
    private UserModel userModel;
    private SignInView view;
    private SignInManager manager;

    public SignInController(SignInManager manager, UserModel userModel) {
        this.manager = manager;
        this.userModel = userModel;
    }

    //region set/get
    public void setView(SignInView view) {
        this.view = view;
    }
    //endregion

    //region events
    @Override
    public void onClickSignIn() {
        if (checkUsername() & checkUsername()) {
            userModel.setUsername(view.getTextUsername());
            userModel.setPassword(view.getTextPass());
            UserManager.loginUser(userModel.getUsername(), userModel.getPassword());
        }else {
            Utilities.showMessage(manager, manager.getString(R.string.auth_error_login_process));
        }
    }

    @Override
    public void onClickSignUp() {
        Intent intent = new Intent(manager, SignUpManager.class);
        intent.putExtra(AppConstants.USER_DATA, userModel);
        manager.startActivityForResult(intent, AppConstants.REQUEST_CODE_SIGN_UP);
    }

    @Override
    public void onBack(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.REQUEST_CODE_SIGN_UP && data != null) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    UserModel model = (UserModel) data.getParcelableExtra(AppConstants.USER_DATA);
                    if (model != null) {
                        System.out.println("OK -> username=" + model.getUsername() + ", password=" + model.getPassword() + ", secret key=" + model.getSecretKey());
                        UserManager.addUser(model.getUsername(), model.getPassword());
                    }
            }
        }
    }
    //endregion

    //region user callback
    @Override
    public void onShowFatalError() {
        Utilities.showMessage(manager, manager.getString(R.string.app_error_fatal));
    }

    @Override
    public void onSuccessRequest(int requestCode) {
        System.out.println(requestCode);
        switch (requestCode) {
            case 200:
                break;
            case 300:
                break;
        }
    }
    //endregion

    //region logic
    private boolean checkUsername() {
        String username = view.getTextUsername();
        return username != null && username.length() >= 2;
    }

    private boolean checkPass() {
        String password = view.getTextPass();
        return password != null && password.length() >= 2;
    }
    //endregion
}
