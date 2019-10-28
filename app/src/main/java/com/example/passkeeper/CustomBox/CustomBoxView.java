package com.example.passkeeper.CustomBox;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.passkeeper.R;

public class CustomBoxView {
    private final View view;
    private TextView txtView_cBoxTitle;
    private TextView txtView_cBoxLogin;
    private TextView txtView_cBoxPassword;
    private ImageView imageView_cBoxIcon;

    public CustomBoxView(View view) {
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        txtView_cBoxTitle = (TextView) view.findViewById(R.id.textView_cBoxTitle);
        txtView_cBoxLogin = (TextView) view.findViewById(R.id.textView_cBoxLogin);
        txtView_cBoxPassword = (TextView) view.findViewById(R.id.textView_cBoxPassword);
        imageView_cBoxIcon =(ImageView) view.findViewById(R.id.imageView_cBoxIcon);
    }

    //region get/set
    public void setTitleText(String text) {
        txtView_cBoxTitle.setText(text);
    }

    public void setLoginText(String text) {
        txtView_cBoxLogin.setText(text);
    }

    public void setPasswordText(String text) {
        txtView_cBoxPassword.setText(text);
    }

    public void setIcon(String uri) {

    }

    public String getTitleText() {
        return txtView_cBoxTitle.getText().toString();
    }

    public String getLoginText() {
        return txtView_cBoxLogin.getText().toString();
    }

    public String getPasswordText() {
        return txtView_cBoxPassword.getText().toString();
    }

    public String getIcon() {
        return null;
    }

    public View getView() {
        return view;
    }
    //endregion
}
