package ru.visualmath.android.login;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.App;
import ru.visualmath.android.R;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    private App app;

    LoginPresenter(App app) {
        this.app = app;
        getViewState().showLoginForm();
    }

    void onLogin(String name, String password) {
        getViewState().showLoading();

        app.getApi().login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getViewState().loginSuccessful(),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                if (((HttpException) throwable).code() == 500) {
                                    getViewState().showError(R.string.wrong_login_or_password);
                                } else {
                                    getViewState().showError(R.string.server_is_not_available);
                                }
                            } else if (throwable instanceof IOException) {
                                getViewState().showError(R.string.check_the_internet_connection);
                            } else {
                                getViewState().showError(R.string.unknown_error);
                            }
                        });
    }

    void onErrorCancel() {
        getViewState().hideError();
    }
}
