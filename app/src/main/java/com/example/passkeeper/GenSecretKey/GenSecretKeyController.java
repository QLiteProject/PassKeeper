package com.example.passkeeper.GenSecretKey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.passkeeper.R;
import com.example.passkeeper.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GenSecretKeyController extends AppCompatActivity {
    private boolean isClipboard = false;
    private GenSecretKeyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_secret_key_form);

        view = new GenSecretKeyView(getWindow().getDecorView(), this);
        generateRandomSecretKey();
        generateWelcomeText();
    }

    //region events
    protected void onClickCreateAccount() {
        if (!isClipboard) {
            showWarningMsg();
        }else {

        }
    }

    protected void onClickEndIconAutoGen() {
        String autoGenText = view.getEditAutoGenText();
        String autoGenTag = getString(R.string.auto_gen_tag);
        Utils.clipBoard(this, autoGenTag, autoGenText);
        Utils.showMessage(this, getString(R.string.n_user_ok_clipboard));
        checkClipboard();
    }

    protected void onClickEndIconEnterKey() {
        String enterKeyText = view.getEditEnterKeyText();
        if (!enterKeyText.isEmpty()) {
            String enterKeyTag = getString(R.string.enter_key_tag);
            Utils.clipBoard(this, enterKeyTag, enterKeyText);
            Utils.showMessage(this, getString(R.string.n_user_ok_clipboard));
            checkClipboard();
        }else {
            Utils.showMessage(this, getString(R.string.n_user_error_clipboard));
        }
    }

    protected void onChangedAutoGen(boolean isChecked) {
        view.setEnabledEditEnterKey(!isChecked);
    }

    protected void onChangedEnterKey(boolean isChecked) {
        view.setEnabledEditAutoGen(!isChecked);
    }
    //endregion

    //region logic
    private void generateRandomSecretKey() {
        String key = Utils.generateRandomHexToken(8);
        view.setEditAutoGenText(key);
    }

    private void generateWelcomeText() {
        String username = "username";
        String text = getString(R.string.n_user_welcome) + " " + username + "!";
        view.setWelcomeText(text);
    }

    private void showWarningMsg() {
        final Context context = this;
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(getString(R.string.n_user_title_warning));
        dialog.setMessage(getString(R.string.n_user_msg_warning));
        dialog.setPositiveButton(getString(R.string.n_user_dbtn_next), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Utils.showMessage(context, getString(R.string.n_user_msg_warning_continue), false);
                isClipboard = true;
                onClickCreateAccount();
            }
        });
        dialog.setNegativeButton(getString(R.string.n_user_dbtn_cancel), null);
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
