package com.example.passkeeper.Main;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.example.passkeeper.Application.AES;
import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.Application.Utilities;
import com.example.passkeeper.CustomBox.CustomBoxAdapter;
import com.example.passkeeper.CustomBox.CustomBoxModel;
import com.example.passkeeper.R;
import com.example.passkeeper.UserAPI.UserModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainController implements MainListener{
    private UserModel userModel;
    private MainManager manager;
    private MainView view;
    private JSONObject base;

    public MainController(MainManager manager, UserModel userModel) {
        this.manager = manager;
        this.userModel = userModel;
        initBase();
    }

    private void initBase() {
        String data = Utilities.getFileText(userModel.getBase());
        String dataDecrypt = AES.decrypt(data, userModel.getSecretKey());
        if (dataDecrypt != null) {
            try {
                this.base = new JSONObject(dataDecrypt);
            }catch (Exception ignored) {

            }
        }
    }

    public void initContent() {
        ArrayList<CustomBoxModel> list = getRecords();
        if (list != null) {
            CustomBoxAdapter adapter = new CustomBoxAdapter(manager, list);
            view.setAdapter(adapter);
        }
    }

    //region get/set
    public void setView(MainView view) {
        this.view = view;
    }
    //endregion

    //region events
    @Override
    public void onClickFloatingBtn() {
        LayoutInflater inflater = manager.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_custom_dialog, null);

        final TextInputLayout txtInTitle = (TextInputLayout) dialogView.findViewById(R.id.textIputLayout_cDialogTitle);
        final TextInputLayout txtInLogin = (TextInputLayout) dialogView.findViewById(R.id.textIputLayout_cDialogLogin);
        final TextInputLayout txtInPassword = (TextInputLayout) dialogView.findViewById(R.id.textIputLayout_cDialogPassword);

        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(manager);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(manager.getString(R.string.c_dialog_title));
        dialogBuilder.setNegativeButton(R.string.c_dialog_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.setPositiveButton(R.string.c_dialog_btn_add_record, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = txtInTitle.getEditText().getText().toString();
                String login = txtInLogin.getEditText().getText().toString();
                String password = txtInPassword.getEditText().getText().toString();
                addRecord(title, login, password);
            }
        });
        dialogBuilder.show();
    }
    //endregion

    //region logic
    private void addRecord(String title, String login, String password) {
        try {
            JSONObject record = new JSONObject()
                    .put(AppConstants.BASE_TITLE, title)
                    .put(AppConstants.BASE_LOGIN, login)
                    .put(AppConstants.BASE_PASSWORD, password);
            JSONArray array = base.getJSONArray(AppConstants.BASE_RECORDS).put(record);
            base.put(AppConstants.BASE_RECORDS, array);
            System.out.println(base);
        }catch (Exception ignored) {

        }
//        try {
//            JSONObject data = base.getJSONObject(AppConstants.BASE);
//            int index = data.optInt(AppConstants.BASE_INDEX);
//            int autoIncrement = index + 1;
//            data.put(AppConstants.BASE_RECORDS,
//                    new JSONObject().put(String.valueOf(autoIncrement), new JSONObject().put(
//                            AppConstants.BASE_TITLE, title
//                            ).put(
//                            AppConstants.BASE_LOGIN, login
//                            ).put(
//                            AppConstants.BASE_PASSWORD, password
//                            )
//                    )
//            );
//            data.put(AppConstants.BASE_INDEX, autoIncrement);
//            base = base.put(AppConstants.BASE, data);
//            System.out.println(base);
//        }catch (Exception ignored) {
//
//        }
    }

    private ArrayList<CustomBoxModel> getRecords() {
        return null;
    }
    //endregion
}
