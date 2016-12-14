package ru.visualmath.android.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    void showLoginForm();

    void showError();

    void showLoading();

    void loginSuccessful();
}