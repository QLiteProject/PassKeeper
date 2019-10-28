package com.example.passkeeper.Main;

import com.example.passkeeper.Application.AES;
import com.example.passkeeper.Application.AppConstants;
import com.example.passkeeper.Application.Utilities;
import com.example.passkeeper.CustomBox.CustomBoxAdapter;
import com.example.passkeeper.CustomBox.CustomBoxModel;
import com.example.passkeeper.UserAPI.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainController implements MainListener{
    private UserModel userModel;
    private CustomBoxAdapter adapter;
    private MainManager manager;
    private MainView view;

    public MainController(MainManager manager) {
        this.manager = manager;
    }

    protected void initContent() {
        adapter = new CustomBoxAdapter(manager, getRecords());
        view.setAdapter(adapter);
    }

    //region get/set
    public void setView(MainView view) {
        this.view = view;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
    //endregion

    protected void parseBase() {
        String data = Utilities.getFileText(userModel.getBase());
        String dataDecrypt = AES.decrypt(data, userModel.getSecretKey());
        if (dataDecrypt != null) {
            try {
                JSONObject base = new JSONObject(dataDecrypt).getJSONObject(AppConstants.USER_BASE);
                System.out.println(base.get(AppConstants.USER_BASE_CREATE_DATE));
            }catch (Exception e) {
                Utilities.showMessage(manager, "ERROR");
            }
        }
    }

    //region logic
    private ArrayList<CustomBoxModel> getRecords() {
        ArrayList<CustomBoxModel> list = new ArrayList<>();
        list.add(new CustomBoxModel(null, "site1", "login1", "password1"));
        list.add(new CustomBoxModel(null, "site2", "login2", "password2"));
        list.add(new CustomBoxModel(null, "site3", "login3", "password3"));
        return list;
    }
    //endregion
}
