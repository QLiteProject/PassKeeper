package com.example.passkeeper.GenPrivateKey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.passkeeper.R;
import com.example.passkeeper.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GenPrivateKeyController extends AppCompatActivity {
    private final int MIN_LENGTH_SECRET_KEY = 10;
    private final int MAX_LENGTH_SECRET_KEY = 20;
    private boolean isClipboard = false;
    private GenPrivateKeyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_privat_key_form);

        view = new GenPrivateKeyView(getWindow().getDecorView(), this);
        generateRandomSecretKey();
        generateWelcomeText();
    }

    //region events
    protected void onClickNext() {
        if (!isClipboard) {
            showWarningMsg();
        }else {

        }
    }

    protected void onClickEndIconAutoGen() {
        String autoGenText = view.getEditAutoGenText();
        String autoGenTag = getString(R.string.auto_gen_tag);
        Utils.clipBoard(this, autoGenTag,autoGenText);
        Utils.showMessage(this, getString(R.string.n_user_ok_clipboard), true);
        checkClipboard();
    }

    protected void onClickEndIconEnterKey() {
        String enterKeyText = view.getEditEnterKeyText();
        if (!enterKeyText.isEmpty()) {
            String enterKeyTag = getString(R.string.enter_key_tag);
            Utils.clipBoard(this, enterKeyTag, enterKeyText);
            Utils.showMessage(this, getString(R.string.n_user_ok_clipboard), true);
            checkClipboard();
        }else {
            Utils.showMessage(this, getString(R.string.n_user_error_clipboard), true);
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
        String keyIn = "qwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&*()-+|'~";
        String keyOut = "";
        for (int i = 0; i < getRandomRange(MIN_LENGTH_SECRET_KEY, MAX_LENGTH_SECRET_KEY); i++) {
            char c = keyIn.charAt(getRandomRange(0, keyIn.length()));
            keyOut += c;
        }
        view.setEditAutoGenText(keyOut);
    }

    private int getRandomRange(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
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
