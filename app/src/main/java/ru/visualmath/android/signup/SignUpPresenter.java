package ru.visualmath.android.signup;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<MvpView> {

    private final VisualMathApi api = VisualMathApi.getApi();

    void onSignUp(String login, String password, String institution, String group) {
        api.createUser(login, password, institution, group)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {

                }, throwable -> {

                });
    }
}
