package com.example.passkeeper.Main;

public class RecordModel {
    private String title;
    private String login;
    private String password;

    public RecordModel(String title, String login, String password) {
        this.title = title;
        this.login = login;
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
