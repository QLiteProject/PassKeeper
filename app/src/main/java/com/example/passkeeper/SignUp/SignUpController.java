package com.example.passkeeper.SignUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.passkeeper.GenSecretKey.GenSecretKeyController;
import com.example.passkeeper.R;
import com.example.passkeeper.Utilities;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SignUpController extends AppCompatActivity {
    private final int REQUEST_CODE_GEN_SECRET_KEY = 2;
    private int minLength;
    private SignUpView view;
    private SignUpModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        view = new SignUpView(getWindow().getDecorView(), this);
        model = new SignUpModel();
        minLength = getResources().getInteger(R.integer.min_length_fields);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GEN_SECRET_KEY) {
            switch (resultCode) {
                case RESULT_OK:
                    if (data != null) {
                        String secretKey = data.getStringExtra(getString(R.string.secret_key));
                        model.setSecretKey(secretKey);
                        onFinish();
                    }else {
                        Utilities.showMessage(this, getString(R.string.app_error_fatal));
                    }
                    break;
                case RESULT_CANCELED:
                    finish();
            }
        }
    }

    private void onFinish() {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.username), model.getUsername());
        intent.putExtra(getString(R.string.password), model.getPassword());
        intent.putExtra(getString(R.string.secret_key), model.getSecretKey());
        setResult(RESULT_OK, intent);
        finish();
    }

    //region events
    protected void onClickNext() {
        if (checkUsername() && checkPass() && checkDuplPass()) {
            if (checkCorrectPass()) {
                if (view.getCheckedLicense()) {
                    Intent intent = new Intent(this, GenSecretKeyController.class);
                    model.setUsername(view.getTextUsername());
                    model.setPassword(view.getTextPass());
                    startActivityForResult(intent, REQUEST_CODE_GEN_SECRET_KEY);
                }else {
                    Utilities.showMessage(this, getString(R.string.reg_error_agreement_canceled));
                }
            }
        }else {
            onChangedUsername();
            onChangedPass();
            onChangedDuplPass();
        }
    }

    protected void onClickBack() {
        finish();
    }

    protected void onClickCheckboxLicense() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(getString(R.string.license_info_title_agreement));
        dialog.setMessage(getString(R.string.license_info_msg_license));
        dialog.setPositiveButton(getString(R.string.license_info_dbtn_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                view.setCheckedLicense(true);
                view.setEnabledCheckboxLicense(false);
            }
        });
        dialog.setNegativeButton(getString(R.string.license_info_dbtn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                view.setCheckedLicense(false);
                view.setEnabledCheckboxLicense(true);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    protected void onChangedUsername() {
        String errMsg = getString(R.string.reg_error_fields);
        if (!checkUsername()) {
            view.setErrMsgUsername(errMsg);
        }else {
            view.setErrMsgUsername(null);
        }
    }

    protected void onChangedPass() {
        String errMsgNonRule = getString(R.string.reg_error_fields);
        String errMsgNonCorr = getString(R.string.reg_error_password_fields);
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

    protected void onChangedDuplPass() {
        String errMsgNonRule = getString(R.string.reg_error_fields);
        String errMsgNonCorr = getString(R.string.reg_error_password_fields);
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
    //endregion

    //region logic
    private boolean checkUsername() {
        String username = view.getTextUsername();
        return username != null && username.length() >= minLength;
    }

    private boolean checkPass() {
        String password = view.getTextPass();
        return password != null && password.length() >= minLength;
    }

    private boolean checkDuplPass() {
        String confirmPassword = view.getTextDuplPass();
        return confirmPassword != null && confirmPassword.length() >= minLength;
    }

    private boolean checkCorrectPass() {
        String password = view.getTextPass();
        String confirmPassword = view.getTextDuplPass();
        return password.equals(confirmPassword);
    }
    //endregion


}
