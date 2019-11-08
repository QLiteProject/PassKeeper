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

    public static void getUserBase(String username, String password) {
        try {
            JSONObject jsonObject = new JSONObject().put(AppConstants.USERNAME, username).put(AppConstants.PASSWORD, password);
            requestPost(UserEvent.GET_RESOURCES, jsonObject.toString());
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }
    }

    public static void setUserBase(String username, String password, String body) {
        try {
            JSONObject jsonObject = new JSONObject().put(AppConstants.USERNAME, username).put(AppConstants.PASSWORD, password).put(AppConstants.USER_DATA, body);
            requestPost(UserEvent.SET_RESOURCES, jsonObject.toString());
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }

    }

    public static void loginUser(String username, String password) {
        try {
            JSONObject jsonObject = new JSONObject().put(AppConstants.USERNAME, username).put(AppConstants.PASSWORD, password);
            requestPost(UserEvent.AUTHORIZATION, jsonObject.toString());
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }
    }

    public static void addUser(String username, String password, String body) {
        try {
            JSONObject jsonObject = new JSONObject().put(AppConstants.USERNAME, username).put(AppConstants.PASSWORD, password).put(AppConstants.USER_DATA, body);
            requestPost(UserEvent.REGISTRATION, jsonObject.toString());
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }
    }

    private static void requestPost(final UserEvent event, String body) {
        String url = AppConstants.URL_SERVER + event.getEvent();
        try {
            StringEntity entity = new StringEntity(body);
            client.post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    callback.onSuccessRequest(event, responseBody);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    callback.onFailureRequest(event);
                }
            });
        }catch (Exception e) {
            if (callback != null) {
                callback.onShowFatalError();
            }
        }
    }

    public enum UserEvent {
        REGISTRATION("/user/registration/"),
        AUTHORIZATION("/user/authorization/"),
        GET_RESOURCES("/user/get_resources/"),
        SET_RESOURCES("/user/set_resources/");

        private String event;

        UserEvent (String event) {
            this.event = event;
        }

        public String getEvent() {
            return event;
        }
    }
}
