package com.example.passkeeper.Settings;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.example.passkeeper.Application.LocaleHelper;
import com.example.passkeeper.Application.ThemeHelper;
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
        ThemeHelper.Themes theme = state ? ThemeHelper.Themes.DARK : ThemeHelper.Themes.LIGHT;
        ThemeHelper.switchTheme(manager, theme);
    }

    private void onClickHome() {
        ThemeHelper.resetSwitchesTheme();
        manager.finish();
    }

    private void onClickSave() {
        saveChangeLanguage(view.getSpinnerItemPosition());
        ThemeHelper.changeTheme(manager);
        manager.setResult(Activity.RESULT_OK);
        manager.finish();
    }
    //endregion

    //region logic
    public void updateContent() {
        ArrayAdapter<String> optionsList = new ArrayAdapter<String>(manager, android.R.layout.simple_spinner_dropdown_item, manager.getResources().getStringArray(R.array.language_option));
        view.setSpinnerLanguageAdapter(optionsList);
        view.setSelectedItem(getCurrentLanguagePosition());
        view.setSwitchChecked(getCurrentThemePosition());
    }

    private void saveChangeLanguage(int position) {
        switch (position) {
            default:
            case 0:
                LocaleHelper.changeLang(manager, LocaleHelper.Locales.ENGLISH);
                break;
            case 1:
                LocaleHelper.changeLang(manager, LocaleHelper.Locales.RUSSIAN);
                break;
        }
    }

    private boolean getCurrentThemePosition() {
        switch (ThemeHelper.getCurrentTheme(manager)) {
            default:
            case DARK:
                return true;
            case LIGHT:
                return false;
        }
    }

    private int getCurrentLanguagePosition() {
        switch (LocaleHelper.getCurrentLocale(manager)) {
            default:
            case ENGLISH:
                return 0;
            case RUSSIAN:
                return 1;
        }
    }
    //endregion
}
