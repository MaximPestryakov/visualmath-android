package ru.visualmath.android.api.model;

public class NewUser {

    private String access;

    private String group;

    private String institution;

    private String login;

    private String password;

    public NewUser(String login, String password, String institution, String group) {
        this.login = login;
        this.password = password;
        this.institution = institution;
        this.group = group;
        this.access = "student";
    }
}
