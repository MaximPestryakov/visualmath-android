package ru.visualmath.android.login;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class LoginViewState implements ViewState<LoginView> {

    public enum LoginState {
        SHOW_LOGIN_FORM, SHOW_ERROR, SHOW_LOADING, LOGIN_SUCCESSFUL
    }

    private LoginState state;
    private Object data;

    @Override
    public void apply(LoginView view, boolean retained) {
        switch (state) {
            case SHOW_LOGIN_FORM:
                view.showLoginForm();
                break;

            case SHOW_ERROR:
                view.showError((String) data);
                break;

            case SHOW_LOADING:
                view.showLoading();
                break;

            case LOGIN_SUCCESSFUL:
                view.loginSuccessful();
                break;
        }
    }

    public void setState(LoginState state) {
        this.state = state;
    }

    public void setState(LoginState state, Object data) {
        this.state = state;
        this.data = data;
    }
}
