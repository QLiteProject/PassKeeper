package com.example.passkeeper.GenSecretKey;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.passkeeper.R;
import com.google.android.material.textfield.TextInputLayout;

public class GenSecretKeyView {
    private final GenSecretKeyListener listener;
    private TextView txt_welcome;
    private RadioButton radBtn_autoGen;
    private RadioButton radBtn_enterKey;
    private TextInputLayout txtIn_autoGen;
    private TextInputLayout txtIn_enterKey;
    private EditText editText_autoGen;
    private EditText editText_enterKey;
    private Button btn_finish;

    public GenSecretKeyView(View view, GenSecretKeyListener listener) {
        this.listener = listener;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        txt_welcome = (TextView) view.findViewById(R.id.textView_nUserWelcomeText);
        radBtn_autoGen = (RadioButton) view.findViewById(R.id.radioButton_nUserAutoGen);
        radBtn_enterKey = (RadioButton) view.findViewById(R.id.radioButton_nUserEnterKey);
        txtIn_autoGen = (TextInputLayout) view.findViewById(R.id.textInputLayout_nUserAutoGen);
        txtIn_enterKey = (TextInputLayout) view.findViewById(R.id.textInputLayout_nUserEnterKey);
        editText_autoGen = txtIn_autoGen.getEditText();
        editText_enterKey = txtIn_enterKey.getEditText();
        btn_finish = (Button) view.findViewById(R.id.button_nUserFinish);
    }

    private void initEvents() {
        radBtn_autoGen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onChangedAutoGen(isChecked);
            }
        });
        radBtn_enterKey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onChangedEnterKey(isChecked);
            }
        });
        txtIn_autoGen.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickEndIconAutoGen();
            }
        });
        txtIn_enterKey.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickEndIconEnterKey();
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCreateAccount();
            }
        });
    }

    //region get/set
    protected void setWelcomeText(String text) {
        txt_welcome.setText(text);
    }

    protected void setEditAutoGenText(String text) {
        editText_autoGen.setText(text);
    }

    protected void setEnabledEditEnterKey(boolean isEnabled) {
        editText_enterKey.setEnabled(isEnabled);
        editText_enterKey.setFocusable(isEnabled);
        editText_enterKey.setClickable(isEnabled);
        editText_enterKey.setCursorVisible(isEnabled);
        editText_enterKey.setFocusableInTouchMode(isEnabled);
    }

    protected void setEnabledEditAutoGen(boolean isEnabled) {
        editText_autoGen.setEnabled(isEnabled);
    }

    protected boolean isCheckedAutoGen() {
        return radBtn_autoGen.isChecked();
    }

    protected boolean isCheckedEnterKey() {
        return radBtn_enterKey.isChecked();
    }

    protected String getEditAutoGenText() {
        return editText_autoGen.getText().toString();
    }

    protected String getEditEnterKeyText() {
        return editText_enterKey.getText().toString();
    }
    //endregion
}
