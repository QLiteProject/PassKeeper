package com.example.passkeeper.EnterSecretKey;

import android.content.Intent;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.Application.Utilities;
import com.example.passkeeper.R;

public class EnterSecretKeyController implements EnterSecretKeyListener{
    EnterSecretKeyView view;
    EnterSecretKeyManager manager;

    public EnterSecretKeyController(EnterSecretKeyManager manager) {
        this.manager = manager;
    }

    //region get/set
    public void setView(EnterSecretKeyView view) {
        this.view = view;
    }
    //endregion

    //region interface
    @Override
    public void onClickNext() {
        if (checkSecret()) {
            Intent intent = new Intent();
            intent.putExtra(AppConstants.SECRET_KEY, view.getSecretText());
            manager.setResult(AppConstants.REQUEST_CODE_ENTER_SECRET_KEY, intent);
            manager.finish();
        }else {
            Utilities.showMessage(manager, manager.getString(R.string.l_user_error_secret_key));
        }
    }
    //endregion

    //region logic
    public void generateWelcomeText() {
        String username = manager.getIntent().getStringExtra(AppConstants.USERNAME);
        String text = manager.getString(R.string.l_user_welcome) + " " + username + "!";
        view.setWelcomeText(text);
    }

    private boolean checkSecret() {
        String secret = view.getSecretText();
        return secret != null && secret.length() >= 2;
    }
    //endregion
}
