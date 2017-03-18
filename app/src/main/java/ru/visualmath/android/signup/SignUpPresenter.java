package ru.visualmath.android.signup;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.model.NewUser;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<MvpView> {

    private VisualMathApi api;

    public SignUpPresenter() {
        api = VisualMathApi.getApi();
    }

    void onSignUp(NewUser newUser) {
        api.createUser(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {

                }, throwable -> {

                });
    }
}
