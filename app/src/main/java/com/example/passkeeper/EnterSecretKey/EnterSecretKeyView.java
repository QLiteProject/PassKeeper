package com.example.passkeeper.EnterSecretKey;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.passkeeper.R;
import com.google.android.material.textfield.TextInputLayout;

public class EnterSecretKeyView {
    private final EnterSecretKeyListener listener;
    private TextView txt_welcome;
    private TextInputLayout txtIn_secret;
    private EditText editText_secret;
    private Button btn_next;

    public EnterSecretKeyView(View view, EnterSecretKeyListener listener) {
        this.listener = listener;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        txt_welcome = (TextView) view.findViewById(R.id.textView_lUserWelcomeText);
        txtIn_secret = (TextInputLayout) view.findViewById(R.id.textInputLayout_lUserSecretKey);
        editText_secret = txtIn_secret.getEditText();
        btn_next = (Button) view.findViewById(R.id.button_lUserNext);
    }

    private void initEvents() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNext();
            }
        });
    }

    //region get/set
    public String getSecretText() {
        return editText_secret.getText().toString();
    }

    public void  setWelcomeText(String text) {
        txt_welcome.setText(text);
    }
    //endregion


}
