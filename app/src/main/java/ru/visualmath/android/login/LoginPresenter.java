package ru.visualmath.android.login;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public void doLogin(String name, String password) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        VisualMathApi.getApi().login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getView().loginSuccessful(),
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
                            getView().showError(errorMessage);
                        });
    }
}
