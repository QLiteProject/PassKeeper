package com.example.passkeeper.UserAPI;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    private String username;
    private String password;
    private String secretKey;
    private String base;

    public UserModel() {
        this.username = null;
        this.password = null;
        this.secretKey = null;
        this.base = null;
    }

    public UserModel(Parcel parcel) {
        String[] data = new String[4];
        parcel.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        this.secretKey = data[2];
        this.base = data[3];
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBase() {
        return base;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {username, password, secretKey, base});
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}