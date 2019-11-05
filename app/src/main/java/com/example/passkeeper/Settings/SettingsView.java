package com.example.passkeeper.Settings;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.passkeeper.R;

public class SettingsView {
    private SettingsListener listener;
    private Spinner spinnerLanguage;
    private Switch switchTheme;

    public SettingsView(View view, SettingsListener listener) {
        this.listener = listener;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        spinnerLanguage = (Spinner) view.findViewById(R.id.spinner_language);
        switchTheme = (Switch) view.findViewById(R.id.switch_setTheme);
    }

    private void initEvents() {
        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChangeTheme(switchTheme.isChecked());
            }
        });
    }

    //region get/set
    public void setSpinnerLanguageAdapter(ArrayAdapter<String> adapter) {
        spinnerLanguage.setAdapter(adapter);
    }

    public int getSpinnerItemPosition() {
        return spinnerLanguage.getSelectedItemPosition();
    }

    public void setSwitchChecked(boolean isChecked) {
        switchTheme.setChecked(isChecked);
    }

    public void setSelectedItem(int position) {
        spinnerLanguage.setSelection(position);
    }
    //endregion
}
