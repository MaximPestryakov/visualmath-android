package ru.visualmath.android.login;

import com.arellomobile.mvp.MvpView;

interface LoginView extends MvpView {

    void showLoginForm();

    void showError(int messageId);

    void showLoading();

    void loginSuccessful();

    void hideError();
}