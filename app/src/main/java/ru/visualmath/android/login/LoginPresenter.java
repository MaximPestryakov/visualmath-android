package ru.visualmath.android.login;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public void doLogin(String name, String password) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        Log.d("LOGIN", name + " " + password);

        //call api
        VisualMathApi.getApi().login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getView().loginSuccessful(),
                        throwable -> getView().showError());
    }
}
