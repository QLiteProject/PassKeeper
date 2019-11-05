package com.example.passkeeper.Settings;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.Application.LocaleHelper;
import com.example.passkeeper.Application.ThemeHelper;
import com.example.passkeeper.Application.Utilities;
import com.example.passkeeper.R;

public class SettingsController implements SettingsListener{
    private SettingsManager manager;
    private SettingsView view;

    public SettingsController(SettingsManager manager) {
        this.manager = manager;
    }

    //region get/set
    public void setView(SettingsView view) {
        this.view = view;
    }
    //endregion

    //region get/set
    public void onClickOptionMenu(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                onClickHome();
                break;
            case R.id.action_save:
                onClickSave();
                break;
        }
    }

    @Override
    public void onChangeTheme(boolean state) {
        int theme = state ? ThemeHelper.DEFAULT_THEME : ThemeHelper.THEME_LIGHT;
        ThemeHelper.changeTheme(manager, theme);
    }

    private void onClickHome() {
        manager.finish();
    }

    private void onClickSave() {
        saveChangeLanguage(view.getSpinnerItemPosition());
//        Utilities.onReload(manager);
        manager.setResult(Activity.RESULT_OK);
        manager.finish();
    }
    //endregion

    //region logic
    public void updateContent() {
        ArrayAdapter<String> optionsList = new ArrayAdapter<String>(manager, android.R.layout.simple_spinner_dropdown_item, manager.getResources().getStringArray(R.array.language_option));
        view.setSpinnerLanguageAdapter(optionsList);
        view.setSelectedItem(getCurrentLanguagePosition());
        view.setSwitchChecked(ThemeHelper.getCurrentTheme(manager) == 0);
    }

    private void saveChangeLanguage(int position) {
        String locale;
        switch (position) {
            default:
            case 0:
                locale = "eu";
                break;
            case 1:
                locale = "ru";
                break;
        }
        LocaleHelper.changeLang(manager, locale);
    }

    private int getCurrentLanguagePosition() {
        String lang = LocaleHelper.getCurrentLang(manager);
        int position;
        switch (lang) {
            default:
            case "eu":
                position = 0;
                break;
            case "ru":
                position = 1;
                break;
        }
        return position;
    }
    //endregion
}
