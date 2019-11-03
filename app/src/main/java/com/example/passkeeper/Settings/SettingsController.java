package com.example.passkeeper.Settings;

import android.widget.ArrayAdapter;

import com.example.passkeeper.Application.LocaleHelper;
import com.example.passkeeper.Application.ThemeHelper;
import com.example.passkeeper.R;

public class SettingsController implements SettingsListener{
    SettingsManager manager;
    SettingsView view;

    public SettingsController(SettingsManager manager) {
        this.manager = manager;
    }

    //region get/set
    public void setView(SettingsView view) {
        this.view = view;
    }
    //endregion

    //region get/set
    @Override
    public void onChangeLanguage(int index) {
        String lang;
        switch (index) {
            default:
            case 0: lang = "en";
                break;
            case 1: lang = "ru";
                break;
        }
        LocaleHelper.changeLang(lang, manager);
    }

    public void onClickOptionMenu(int index) {

    }

    @Override
    public void onChangeTheme(boolean state) {
        ThemeHelper.changeTheme(manager, state ? ThemeHelper.THEME_DEFAULT : ThemeHelper.THEME_LIGHT);
    }

    //endregion

    public void updateContent() {
        ArrayAdapter<String> optionsList = new ArrayAdapter<String>(manager, android.R.layout.simple_spinner_dropdown_item, manager.getResources().getStringArray(R.array.language_option));
        view.setSpinnerLanguageAdapter(optionsList);
    }
}
