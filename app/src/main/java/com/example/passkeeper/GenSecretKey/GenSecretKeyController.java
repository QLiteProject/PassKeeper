package com.example.passkeeper.GenSecretKey;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.R;
import com.example.passkeeper.Application.Utilities;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GenSecretKeyController implements GenSecretKeyListener {
    private boolean isClipboard = false;
    private String secret = null;
    private GenSecretKeyView view;
    private GenSecretKeyManager manager;

    public GenSecretKeyController(GenSecretKeyManager manager) {
        this.manager = manager;
    }

    //region get/set
    public void setView(GenSecretKeyView view) {
        this.view = view;
    }
    //endregion

    //region events
    @Override
    public void onClickCreateAccount() {
        if (!isClipboard) {
            showWarningMsg();
        }else {
            if (view.isCheckedAutoGen()) {
                secret = view.getEditAutoGenText();
                onFinish();
            }else if (view.isCheckedEnterKey()) {
                secret = view.getEditEnterKeyText();
                onFinish();
            }
        }
    }

    @Override
    public void onClickEndIconAutoGen() {
        String autoGenText = view.getEditAutoGenText();
        Utilities.clipBoard(manager, AppConstants.AUTO_GEN_TAG, autoGenText);
        Utilities.showMessage(manager, manager.getString(R.string.n_user_ok_clipboard));
        checkClipboard();
    }

    @Override
    public void onClickEndIconEnterKey() {
        String enterKeyText = view.getEditEnterKeyText();
        if (!enterKeyText.isEmpty()) {
            Utilities.clipBoard(manager, AppConstants.ENTER_KEY_TAG, enterKeyText);
            Utilities.showMessage(manager, manager.getString(R.string.n_user_ok_clipboard));
            checkClipboard();
        }else {
            Utilities.showMessage(manager, manager.getString(R.string.n_user_error_clipboard));
        }
    }

    @Override
    public void onChangedAutoGen(boolean isChecked) {
        view.setEnabledEditAutoGen(!isChecked);
    }

    @Override
    public void onChangedEnterKey(boolean isChecked) {
        view.setEnabledEditEnterKey(!isChecked);
    }

    private void onFinish() {
        if (secret != null && !secret.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(AppConstants.SECRET_KEY, secret);
            manager.setResult(Activity.RESULT_OK, intent);
            manager.finish();
        }else {
            Utilities.showMessage(manager, manager.getString(R.string.n_user_error_secret_key));
        }
    }
    //endregion

    //region logic
    public void generateRandomSecretKey() {
        String key = Utilities.generateRandomHexToken(AppConstants.SECRET_BYTE);
        view.setEditAutoGenText(key);
    }

    public void generateWelcomeText() {
        String username = manager.getIntent().getStringExtra(AppConstants.USERNAME);
        String text = manager.getString(R.string.n_user_welcome) + " " + username + "!";
        view.setWelcomeText(text);
    }

    private void showWarningMsg() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(manager);
        dialog.setTitle(manager.getString(R.string.n_user_title_warning));
        dialog.setMessage(manager.getString(R.string.n_user_msg_warning));
        dialog.setPositiveButton(manager.getString(R.string.n_user_dbtn_next), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Utilities.showMessage(manager, manager.getString(R.string.n_user_msg_warning_continue), false);
                isClipboard = true;
                onClickCreateAccount();
            }
        });
        dialog.setNegativeButton(manager.getString(R.string.n_user_dbtn_cancel), null);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void checkClipboard() {
        if (!isClipboard) {
            isClipboard = true;
        }
    }
    //endregion
}
