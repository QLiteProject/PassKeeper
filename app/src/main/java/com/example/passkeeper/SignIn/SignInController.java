package com.example.passkeeper.SignIn;

import android.content.Intent;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.EnterSecretKey.EnterSecretKeyManager;
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

    public SignInController(SignInManager manager) {
        this.manager = manager;
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
            userModel = new UserModel();
            userModel.setUsername(view.getTextUsername());
            userModel.setPassword(view.getTextPass());
            UserManager.loginUser(userModel.getUsername(), userModel.getPassword());
            Utilities.showMessage(manager, manager.getString(R.string.auth_login_process));
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

    protected void onBack(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_ENTER_SECRET_KEY:
                    onFinishSignIn(data);
                    break;
                case AppConstants.REQUEST_CODE_SIGN_UP:
                    onFinishSignUp(data);
                    break;
            }
        }
    }

    private void onFinishSignIn(Intent data) {
        String secret = data.getStringExtra(AppConstants.SECRET_KEY);
        if (secret != null) {
            userModel.setSecretKey(secret);
            decryptionBase();
        }
    }

    private void onFinishSignUp(Intent data) {
        UserModel model = (UserModel) data.getParcelableExtra(AppConstants.USER_DATA);
        if (model != null) {
            System.out.println("OK -> username=" + model.getUsername() + ", password=" + model.getPassword() + ", secret key=" + model.getSecretKey());
            userModel.setSecretKey(model.getSecretKey());
            UserManager.addUser(model.getUsername(), model.getPassword());
            Utilities.showMessage(manager, manager.getString(R.string.auth_login_process));
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
        System.out.println("Request -> code : " + requestCode);
        switch (requestCode) {
            case 200:
                prepareContent();
                break;
            case 421:
                System.out.println("ERROR -> request : 421 -> username=" + userModel.getUsername() + ", password=" + userModel.getPassword() + ", secret key=" + userModel.getSecretKey());
                userModel = new UserModel();
                Utilities.showMessage(manager, manager.getString(R.string.auth_error_request_421));
                break;
        }
    }
    //endregion

    //region prepare
    private void prepareContent() {
        if (userModel.getSecretKey() == null) {
            Intent intent = new Intent(manager, EnterSecretKeyManager.class);
            intent.putExtra(AppConstants.USERNAME, userModel.getUsername());
            manager.startActivityForResult(intent, AppConstants.REQUEST_CODE_ENTER_SECRET_KEY);
        }else {
            System.out.println("OK -> prepare content : START -> username=" + userModel.getUsername() + ", password=" + userModel.getPassword() + ", secret key=" + userModel.getSecretKey());
            decryptionBase();
        }
    }
    
    private void decryptionBase() {
        System.out.println("OK -> decryption base : start");
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
