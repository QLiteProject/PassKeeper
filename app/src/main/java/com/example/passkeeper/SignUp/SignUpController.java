package com.example.passkeeper.SignUp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.GenSecretKey.GenSecretKeyManager;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserModel;
import com.example.passkeeper.Application.Utilities;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SignUpController implements SignUpListener {
    private UserModel userModel;
    private SignUpView view;
    private SignUpManager manager;

    public SignUpController(SignUpManager manager, UserModel userModel) {
        this.manager = manager;
        this.userModel = userModel;
    }

    //region set/get
    public void setView(SignUpView view) {
        this.view = view;
    }
    //endregion

    //region events
    public void onClickNext() {
        if (checkUsername() && checkPass() && checkDuplPass()) {
            if (checkCorrectPass()) {
                if (view.getCheckedLicense()) {
                    Intent intent = new Intent(manager, GenSecretKeyManager.class);
                    userModel.setUsername(view.getTextUsername());
                    userModel.setPassword(view.getTextPass());
                    intent.putExtra(AppConstants.USERNAME, userModel.getUsername());
                    manager.startActivityForResult(intent, AppConstants.REQUEST_CODE_GEN_SECRET_KEY);
                }else {
                    Utilities.showMessage(manager, manager.getString(R.string.reg_error_agreement_canceled));
                }
            }
        }else {
            onChangedUsername();
            onChangedPass();
            onChangedDuplPass();
        }
    }

    public void onClickBack() {
        manager.finish();
    }

    public void onClickCheckboxLicense() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(manager);
        dialog.setTitle(manager.getString(R.string.license_info_title_agreement));
        dialog.setMessage(manager.getString(R.string.license_info_msg_license));
        dialog.setPositiveButton(manager.getString(R.string.license_info_dbtn_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                view.setCheckedLicense(true);
                view.setEnabledCheckboxLicense(false);
            }
        });
        dialog.setNegativeButton(manager.getString(R.string.license_info_dbtn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                view.setCheckedLicense(false);
                view.setEnabledCheckboxLicense(true);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public void onChangedUsername() {
        String errMsg = manager.getString(R.string.reg_error_fields);
        if (!checkUsername()) {
            view.setErrMsgUsername(errMsg);
        }else {
            view.setErrMsgUsername(null);
        }
    }

    public void onChangedPass() {
        String errMsgNonRule = manager.getString(R.string.reg_error_fields);
        String errMsgNonCorr = manager.getString(R.string.reg_error_password_fields);
        if (!checkPass()) {
            view.setErrMsgPass(errMsgNonRule);
            if (checkDuplPass()) {
                view.setErrMsgDuplPass(null);
            }
        }else {
            if (checkDuplPass()) {
                if (!checkCorrectPass()) {
                    view.setErrMsgDuplPass(errMsgNonCorr);
                    view.setErrMsgPass(errMsgNonCorr);
                }else {
                    view.setErrMsgDuplPass(null);
                    view.setErrMsgPass(null);
                }
            }else {
                view.setErrMsgPass(null);
            }
        }
    }

    public void onChangedDuplPass() {
        String errMsgNonRule = manager.getString(R.string.reg_error_fields);
        String errMsgNonCorr = manager.getString(R.string.reg_error_password_fields);
        if (!checkDuplPass()) {
            view.setErrMsgDuplPass(errMsgNonRule);
            if (checkPass()) {
                view.setErrMsgPass(null);
            }
        }else {
            if (checkPass()) {
                if (!checkCorrectPass()) {
                    view.setErrMsgPass(errMsgNonCorr);
                    view.setErrMsgDuplPass(errMsgNonCorr);
                }else {
                    view.setErrMsgPass(null);
                    view.setErrMsgDuplPass(null);
                }
            }else {
                view.setErrMsgDuplPass(null);
            }
        }
    }

    protected void onBack(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.REQUEST_CODE_GEN_SECRET_KEY) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data != null) {
                        String secret = data.getStringExtra(AppConstants.SECRET_KEY);
                        userModel.setSecretKey(secret);
                        onFinish();
                    }else {
                        Utilities.showMessage(manager, manager.getString(R.string.app_error_fatal));
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    manager.finish();
            }
        }
    }

    private void onFinish() {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.USER_DATA, userModel);
        manager.setResult(Activity.RESULT_OK, intent);
        manager.finish();
    }
    //endregion

    //region logic
    private boolean checkUsername() {
        String username = view.getTextUsername();
        return username != null && username.length() >= AppConstants.MIN_LENGTH_FIELDS;
    }

    private boolean checkPass() {
        String password = view.getTextPass();
        return password != null && password.length() >= AppConstants.MIN_LENGTH_FIELDS;
    }

    private boolean checkDuplPass() {
        String confirmPassword = view.getTextDuplPass();
        return confirmPassword != null && confirmPassword.length() >= AppConstants.MIN_LENGTH_FIELDS;
    }

    private boolean checkCorrectPass() {
        String password = view.getTextPass();
        String confirmPassword = view.getTextDuplPass();
        return password.equals(confirmPassword);
    }
    //endregion
}
