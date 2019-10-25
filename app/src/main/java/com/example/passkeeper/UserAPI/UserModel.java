package com.example.passkeeper.UserAPI;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    private String username;
    private String password;
    private String secretKey;

    public UserModel() {
        this.username = null;
        this.password = null;
        this.secretKey = null;
    }

    public UserModel(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);
        username = data[0];
        password = data[1];
        secretKey = data[2];
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {username, password, secretKey});
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
