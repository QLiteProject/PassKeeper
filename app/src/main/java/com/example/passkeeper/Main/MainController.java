package com.example.passkeeper.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.passkeeper.Application.AES;
import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.Application.Utilities;
import com.example.passkeeper.Main.CustomBox.CustomBoxAdapter;
import com.example.passkeeper.Main.CustomBox.CustomBoxListener;
import com.example.passkeeper.Main.CustomBox.RecordModel;
import com.example.passkeeper.Main.CustomDialog.CustomDialogController;
import com.example.passkeeper.R;
import com.example.passkeeper.Settings.SettingsManager;
import com.example.passkeeper.UserAPI.UserCallback;
import com.example.passkeeper.UserAPI.UserManager;
import com.example.passkeeper.UserAPI.UserModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainController implements MainListener, CustomBoxListener, UserCallback {
    private UserModel userModel;
    private MainManager manager;
    private MainView view;
    private JSONArray records;
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
                base = new JSONObject(dataDecrypt);
                records = base.getJSONObject(AppConstants.BASE).getJSONArray(AppConstants.BASE_RECORDS);
            }catch (Exception ignored) {
                Utilities.showMessage(manager, manager.getString(R.string.main_error_base));
            }
        }
    }

    //region get/set
    public void setView(MainView view) {
        this.view = view;
    }
    //endregion

    //region events
    @Override
    public void onClickFloatingAddRecord() {
        DialogCallback callback = new DialogCallback() {
            @Override
            public void onClickDialogAdd(RecordModel model) {
                updateRecord(model, null);
            }
        };
        onShowDialogRecordAdd(callback, null);
    }

    @Override
    public void onClickOptionUpload() {
        String data = Utilities.getFileText(userModel.getBase());
        UserManager.setUserBase(userModel.getUsername(), userModel.getPassword(), data);
        Utilities.showMessage(manager, manager.getString(R.string.main_msg_synchronized_process));
    }

    @Override
    public void onClickOptionDownload() {
        UserManager.getUserBase(userModel.getUsername(), userModel.getPassword());
        Utilities.showMessage(manager, manager.getString(R.string.main_msg_synchronized_process));
    }

    @Override
    public void onClickOptionSettings() {
        Intent intent = new Intent(manager, SettingsManager.class);
        manager.startActivityForResult(intent, AppConstants.REQUEST_CODE_SETTINGS);
    }

    @Override
    public void onClickDelete(int index) {
        try {
            JSONArray list = new JSONArray();
            for (int i = 0; i < records.length(); i++) {
                if (i != index) {
                    list.put(records.get(i));
                }
            }
            records = list;
            updateAll();
        }catch (Exception ignored) {
            Utilities.showMessage(manager, manager.getString(R.string.main_error_delete_record));
        }
    }

    @Override
    public void onClickEdit(final int index) {
        DialogCallback callback = new DialogCallback() {
            @Override
            public void onClickDialogAdd(RecordModel model) {
                updateRecord(model, index);
            }
        };
        try {
            JSONObject record = records.getJSONObject(index);
            RecordModel model = new RecordModel(
                    record.optString(AppConstants.BASE_TITLE),
                    record.optString(AppConstants.BASE_LOGIN),
                    record.optString(AppConstants.BASE_PASSWORD)
            );
            onShowDialogRecordAdd(callback, model);
        }catch (Exception ignored) {
            Utilities.showMessage(manager, manager.getString(R.string.main_error_update_record));
        }
    }


    public void onClickOptionMenu(int itemId) {
        switch (itemId) {
            case R.id.action_settings:
                onClickOptionSettings();
                break;
            case R.id.action_upload:
                onClickOptionUpload();
                break;
            case R.id.action_download:
                onClickOptionDownload();
                break;
        }
    }

    public void onBack(int requestCode, int resultCode, Intent data) {
        manager.recreate();
    }

    //endregion

    //region logic
    public void updateAll() {
        updateContent();
        updateResources();
    }

    private void updateContent() {
        ArrayList<RecordModel> objects = getRecords();
        if (objects != null) {
            CustomBoxAdapter adapter = new CustomBoxAdapter(manager, objects, this);
            view.setAdapterListView(adapter);
        }
    }

    private void updateResources() {
        try {
            base.getJSONObject(AppConstants.BASE).put(AppConstants.BASE_RECORDS, records);
            String dataEncrypt = AES.encrypt(base.toString(), userModel.getSecretKey());
            if (!Utilities.setFileText(userModel.getBase(), dataEncrypt)) {
                Utilities.showMessage(manager, manager.getString(R.string.main_error_update_local_base));
            }
        }catch (Exception ignored) {
            Utilities.showMessage(manager, manager.getString(R.string.main_error_base));
        }
    }

    private void getResourcesProcess(byte[] body) {
        try {
            JSONObject data = new JSONObject(new String(body));
            String dataEncrypt = data.get(AppConstants.BASE).toString();
            if (userModel.getSecretKey() == null | AES.decrypt(dataEncrypt, userModel.getSecretKey()) == null) {
                Utilities.showMessage(manager, manager.getString(R.string.auth_error_decrypt_base));
            }else {
                Utilities.setFileText(userModel.getBase(), dataEncrypt);
                Utilities.showMessage(manager, manager.getString(R.string.main_msg_ok_synchronized));
                manager.recreate();
            }
        }catch (Exception ignored) {
            Utilities.showMessage(manager, manager.getString(R.string.app_error_fatal));
        }
    }

    private void updateRecord(RecordModel model, Integer index) {
        if (model.getTitle().isEmpty() & model.getLogin().isEmpty() & model.getPassword().isEmpty()) {
            return;
        }
        try {
            JSONObject record = new JSONObject()
                    .put(AppConstants.BASE_TITLE, model.getTitle())
                    .put(AppConstants.BASE_LOGIN, model.getLogin())
                    .put(AppConstants.BASE_PASSWORD, model.getPassword());
            if (index != null) {
                records.put(index, record);
            }else {
                records.put(record);
            }
            updateAll();
        }catch (Exception ignored) {
            Utilities.showMessage(manager, manager.getString(R.string.main_error_update_record));
        }
    }

    private ArrayList<RecordModel> getRecords() {
        ArrayList<RecordModel> model = new ArrayList<>();
        for (int i = 0; i < records.length(); i++) {
            try {
                JSONObject record = records.getJSONObject(i);
                RecordModel item = new RecordModel(
                        record.optString(AppConstants.BASE_TITLE),
                        record.optString(AppConstants.BASE_LOGIN),
                        record.optString(AppConstants.BASE_PASSWORD)
                );
                model.add(item);
            }catch (Exception ignored) {
                Utilities.showMessage(manager, manager.getString(R.string.main_error_generate_list));
            }
        }
        return model;
    }

    private void onShowDialogRecordAdd(final DialogCallback listener, RecordModel model) {
        LayoutInflater inflater = manager.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final CustomDialogController dialogController = new CustomDialogController(dialogLayout);

        if (model != null) {
            dialogController.setTitle(model.getTitle());
            dialogController.setLogin(model.getLogin());
            dialogController.setPassword(model.getPassword());
        }

        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(manager);
        dialogBuilder.setView(dialogController.getView());
        dialogBuilder.setTitle(manager.getString(R.string.c_dialog_record_editor));
        dialogBuilder.setNegativeButton(R.string.c_dialog_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.setPositiveButton(R.string.c_dialog_btn_add_record, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecordModel model = new RecordModel(dialogController.getTitle(), dialogController.getLogin(), dialogController.getPassword());
                listener.onClickDialogAdd(model);
            }
        });
        dialogBuilder.show();
    }

    @Override
    public void onShowFatalError() {
        Utilities.showMessage(manager, manager.getString(R.string.app_error_fatal));
    }

    @Override
    public void onSuccessRequest(UserManager.UserEvent userEvent, byte[] body) {
        switch (userEvent) {
            case SET_RESOURCES:
                Utilities.showMessage(manager, manager.getString(R.string.main_msg_ok_synchronized));
                break;
            case GET_RESOURCES:
                getResourcesProcess(body);
                break;
        }
    }

    @Override
    public void onFailureRequest(UserManager.UserEvent userEvent) {
        Utilities.showMessage(manager, manager.getString(R.string.auth_error_request));
    }
    //endregion

    //region callback
    interface DialogCallback {
        void onClickDialogAdd(RecordModel model);
    }
    //endregion
}
