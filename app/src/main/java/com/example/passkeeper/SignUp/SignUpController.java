package com.example.passkeeper.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.passkeeper.R;
import com.example.passkeeper.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SignUpController extends AppCompatActivity {
    private int minLength;
    private SignUpView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        view = new SignUpView(getWindow().getDecorView(), this);
        minLength = getResources().getInteger(R.integer.min_length);
    }

    //region events
    protected void onClickCreateAccount() {
        String username = view.getTextUsername();
        String pass = view.getTextPass();
        if (checkUsername() && checkPass() && checkDuplPass()) {
            if (checkCorrectPass()) {
                if (view.getCheckedLicense()) {
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.username), username);
                    intent.putExtra(getString(R.string.password), pass);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Utils.showMessage(this, getString(R.string.reg_error_agreement_canceled), true);
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
    protected boolean checkUsername() {
        String username = view.getTextUsername();
        return username != null && username.length() >= minLength;
    }

    protected boolean checkPass() {
        String password = view.getTextPass();
        return password != null && password.length() >= minLength;
    }

    protected boolean checkDuplPass() {
        String confirmPassword = view.getTextDuplPass();
        return confirmPassword != null && confirmPassword.length() >= minLength;
    }

    protected boolean checkCorrectPass() {
        String password = view.getTextPass();
        String confirmPassword = view.getTextDuplPass();
        return password.equals(confirmPassword);
    }
    //endregion


}
