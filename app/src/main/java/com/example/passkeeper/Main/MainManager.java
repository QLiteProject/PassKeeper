package com.example.passkeeper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserModel;

public class MainManager extends AppCompatActivity {
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        UserModel userModel = getIntent().getParcelableExtra(AppConstants.USER_DATA);
        controller = new MainController(this);
        MainView view = new MainView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.setUserModel(userModel);
        controller.initContent();
        controller.parseBase();
    }
}
