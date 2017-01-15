package ru.visualmath.android.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

interface LoginView extends MvpView {

    void showLoginForm();

    void showError(String message);

    void showLoading();

    void loginSuccessful();
}