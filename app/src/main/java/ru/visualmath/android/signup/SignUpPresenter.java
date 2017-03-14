package ru.visualmath.android.signup;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.App;
import ru.visualmath.android.api.model.NewUser;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<MvpView> {

    private App app;

    SignUpPresenter(App app) {
        this.app = app;
    }

    void onSignUp(NewUser newUser) {
        app.getApi().createUser(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {

                }, throwable -> {

                });
    }
}
