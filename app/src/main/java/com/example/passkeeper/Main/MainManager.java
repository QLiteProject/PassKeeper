package com.example.passkeeper.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserManager;
import com.example.passkeeper.UserAPI.UserModel;

public class MainManager extends AppCompatActivity {
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        initToolbar();

        UserModel userModel = getIntent().getParcelableExtra(AppConstants.USER_DATA);
        controller = new MainController(this, userModel);
        MainView view = new MainView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.updateAll();
        UserManager.callback = controller;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        controller.onClickOptionMenu(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
