package com.example.passkeeper.UserAPI;

import com.example.passkeeper.Application.AppConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class UserManager {
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static UserCallback callback;

    public static void loginUser(String username, String password) {
        try {
            String url = AppConstants.URL_SERVER + UserEvent.AUTHORIZATION.getEvent();
            JSONObject jsonObject = new JSONObject().put(AppConstants.USERNAME, username).put(AppConstants.PASSWORD, password);
            StringEntity entity = new StringEntity(jsonObject.toString());
            requestPost(url, entity);
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }
    }

    public static void addUser(String username, String password) {
        try {
            String url = AppConstants.URL_SERVER + UserEvent.REGISTRATION.getEvent();
            JSONObject jsonObject = new JSONObject().put(AppConstants.USERNAME, username).put(AppConstants.PASSWORD, password);
            System.out.println(jsonObject.toString());
            StringEntity entity = new StringEntity(jsonObject.toString());
            requestPost(url, entity);
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }
    }

    private static void requestPost(String url, StringEntity entity) {
        client.post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callback.onSuccessRequest(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onSuccessRequest(statusCode);
            }
        });
    }

    public enum UserEvent {
        REGISTRATION("/user/registration/"),
        AUTHORIZATION("/user/authorization/");

        protected String event;

        UserEvent (String event) {
            this.event = event;
        }

        public String getEvent() {
            return event;
        }
    }
}
