package com.example.passkeeper.SignIn;

import android.content.Intent;

import com.example.passkeeper.Application.AES;
import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.EnterSecretKey.EnterSecretKeyManager;
import com.example.passkeeper.Main.MainManager;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserCallback;
import com.example.passkeeper.SignUp.SignUpManager;
import com.example.passkeeper.UserAPI.UserManager;
import com.example.passkeeper.UserAPI.UserModel;
import com.example.passkeeper.Application.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

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
        userModel = new UserModel();
        Intent intent = new Intent(manager, SignUpManager.class);
        intent.putExtra(AppConstants.USER_DATA, userModel);
        manager.startActivityForResult(intent, AppConstants.REQUEST_CODE_SIGN_UP);
    }

    public void onBack(int requestCode, int resultCode, Intent data) {
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
            signInProcess();
        }
    }

    private void onFinishSignUp(Intent data) {
        UserModel model = (UserModel) data.getParcelableExtra(AppConstants.USER_DATA);
        if (model != null) {
            userModel.setSecretKey(model.getSecretKey());
            userModel.setUsername(model.getUsername());
            userModel.setPassword(model.getPassword());
            signUpProcess();
        }
    }

    private void onOK() {
        Intent intent = new Intent(manager, MainManager.class);
        intent.putExtra(AppConstants.USER_DATA, userModel);
        manager.startActivity(intent);
        manager.finish();
    }
    //endregion

    //region user callback
    @Override
    public void onShowFatalError() {
        Utilities.showMessage(manager, manager.getString(R.string.app_error_fatal));
    }

    @Override
    public void onSuccessRequest(UserManager.UserEvent userEvent, byte[] body) {
        switch (userEvent) {
            case REGISTRATION:
                registrationProcess();
                break;
            case AUTHORIZATION:
                authorizationProcess();
                break;
            case GET_RESOURCES:
                getResourcesProcess(body);
                break;
        }
    }

    @Override
    public void onFailureRequest(UserManager.UserEvent userEvent) {
        Utilities.showMessage(manager, manager.getString(R.string.auth_error_request));
    }
    //endregion

    //region prepare
    private void signUpProcess() {
        String data = getDefaultBaseConstruction();
        if (data != null) {
            String dataEncrypt = AES.encrypt(data, userModel.getSecretKey());
            UserManager.addUser(userModel.getUsername(), userModel.getPassword(), dataEncrypt);
        }
    }

    private void signInProcess() {
        UserManager.getUserBase(userModel.getUsername(), userModel.getPassword());
    }

    private void registrationProcess() {
        UserManager.getUserBase(userModel.getUsername(), userModel.getPassword());
    }

    private void authorizationProcess() {
        Intent intent = new Intent(manager, EnterSecretKeyManager.class);
        intent.putExtra(AppConstants.USERNAME, userModel.getUsername());
        manager.startActivityForResult(intent, AppConstants.REQUEST_CODE_ENTER_SECRET_KEY);
    }

    private void getResourcesProcess(byte[] body) {
        try {
            JSONObject data = new JSONObject(new String(body));
            String localPath = AppConstants.APP_FOLDER + File.separator + data.optString(AppConstants.BASE_NAME);
            File file = new File(localPath);
            if (file.exists()) {
                userModel.setBase(localPath);
                onOK();
            }else {
                if (Utilities.createFile(localPath, data.get(AppConstants.BASE).toString())) {
                    userModel.setBase(localPath);
                    onOK();
                }else {
                    Utilities.showMessage(manager, "Error local base");
                }
            }
        }catch (Exception ignored) {
            Utilities.showMessage(manager, manager.getString(R.string.app_error_fatal));
        }
    }
    //endregion

    //region logic
    public void createAppFolder() {
        Utilities.createDir(AppConstants.APP_FOLDER);
    }

    private String getDefaultBaseConstruction() {
        try {
            return new JSONObject().put(
                    AppConstants.BASE, new JSONObject()
                            .put(AppConstants.BASE_CREATE_DATE, Utilities.getCurrentDateTimeAsString())
                            .put(AppConstants.BASE_RECORDS, new JSONArray())
            ).toString();
        }catch (Exception ignored) {
            return null;
        }
    }

    private boolean checkUsername() {
        String username = view.getTextUsername();
        return username != null && username.length() >= 2;
    }
    //endregion
}
