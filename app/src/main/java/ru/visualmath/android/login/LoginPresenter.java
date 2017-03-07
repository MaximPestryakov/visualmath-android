package ru.visualmath.android.login;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.App;

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
                            String errorMessage;
                            if (throwable instanceof HttpException) {
                                if (((HttpException) throwable).code() == 500) {
                                    errorMessage = "Неверный логин или пароль";
                                } else {
                                    errorMessage = "Сервер временно недоступен";
                                }
                            } else if (throwable instanceof IOException) {
                                errorMessage = "Проверьте подключение к интернету";
                            } else {
                                errorMessage = "Неизвестная ошибка";
                            }
                            getViewState().showError(errorMessage);
                        });
    }

    void onErrorCancel() {
        getViewState().hideError();
    }
}
