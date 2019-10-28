package com.example.passkeeper.CustomBox;

public class CustomBoxModel {
    private String icon;
    private String title;
    private String login;
    private String password;

    public CustomBoxModel(String icon, String paragraph, String login, String password) {
        this.icon = icon;
        this.title = paragraph;
        this.login = login;
        this.password = password;
    }

    public String getIcon() {
        return icon;
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
