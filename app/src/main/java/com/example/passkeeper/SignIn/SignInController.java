package com.example.passkeeper.SignIn;

import android.content.Intent;

import com.example.passkeeper.Application.AES;
import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.Main.MainManager;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserCallback;
import com.example.passkeeper.SignUp.SignUpManager;
import com.example.passkeeper.UserAPI.UserManager;
import com.example.passkeeper.UserAPI.UserModel;
import com.example.passkeeper.Application.Utilities;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

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
        }
    }

    private void onFinishSignUp(Intent data) {
        UserModel model = (UserModel) data.getParcelableExtra(AppConstants.USER_DATA);
        if (model != null) {
            System.out.println("OK -> username=" + model.getUsername() + ", password=" + model.getPassword() + ", secret key=" + model.getSecretKey());
            userModel.setSecretKey(model.getSecretKey());
            userModel.setUsername(model.getUsername());
            userModel.setPassword(model.getPassword());
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
    public void onSuccessRequest(UserManager.UserEvent userEvent) {
        switch (userEvent) {
            case REGISTRATION:
                registrationProcess();
                break;
            case AUTHORIZATION:
                break;
            case GET_RESOURCES:
                break;
            case SET_RESOURCES:
                setResourcesProgress();
                break;
        }
    }

    @Override
    public void onFailureRequest(UserManager.UserEvent userEvent) {
        switch (userEvent) {
            case REGISTRATION:
            case AUTHORIZATION:
            case GET_RESOURCES:
            case SET_RESOURCES:
                Utilities.showMessage(manager, "onFailureRequest -> ERROR");
                break;
        }
    }
    //endregion

    //region prepare
    private void onOK(String uri) {
        Intent intent = new Intent(manager, MainManager.class);
        intent.putExtra(AppConstants.USER_BASE, uri);
        manager.startActivity(intent);
        manager.finish();
    }

    private void setResourcesProgress() {
        onOK(null);
    }

    private void registrationProcess() {
        String path = compileUserData();
        if (path != null & Utilities.isFileExists(path)) {
            String body = Utilities.getFileText(path);
            UserManager.setUserBase(userModel.getUsername(), userModel.getPassword(), body);
        }else {
            Utilities.showMessage(manager, "registrationProcess -> ERROR");
        }
    }

    private String compileUserData() {
        String path = AppConstants.APP_FOLDER + File.separator + Utilities.generateRandomHexToken(8);
        File file = new File(path);
        if (!file.exists()) {
            String data = getDefaultEncryptJSON();
            System.out.println("getDefaultEncryptJSON -> data: " + data);
            if (data != null) {
                try {
                    if (file.createNewFile()) {
                        FileOutputStream fileOut = new FileOutputStream(file, false);
                        fileOut.write(data.getBytes());
                        fileOut.close();
                        return path;
                    }else {
                        return null;
                    }
                }catch (Exception ignored) {
                    return null;
                }
            }else{
                return null;
            }
        }else {
            return compileUserData();
        }
    }
    //endregion

    //region logic
    private String getDefaultEncryptJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("base",
                    new JSONObject().put("create_date", Utilities.getCurrentDateTimeAsString())
            );
            return AES.encrypt(jsonObject.toString(), userModel.getSecretKey());
        }catch (Exception e) {
            Utilities.showMessage(manager, "getDefaultEncryptJSON -> ERROR");
            return null;
        }
    }

    public void createAppFolder() {
        Utilities.createDir(AppConstants.APP_FOLDER);
    }

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
