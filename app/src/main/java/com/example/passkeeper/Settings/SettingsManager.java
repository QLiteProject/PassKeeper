package com.example.passkeeper.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.passkeeper.Application.LocaleHelper;
import com.example.passkeeper.Application.ThemeHelper;
import com.example.passkeeper.R;

public class SettingsManager extends AppCompatActivity {
    private SettingsController controller;
    private SettingsView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.loadLocale(this);
        ThemeHelper.loadTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_form);

        initToolbar();

        controller = new SettingsController(this);
        view = new SettingsView(getWindow().getDecorView(), controller);
        controller.setView(view);
        controller.updateContent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.main_menu_hint_settings));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        controller.onClickOptionMenu(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
