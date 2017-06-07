package ru.visualmath.android.signup;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<SignUpView> {

    private final VisualMathApi api = VisualMathApi.getApi();

    void onSignUp(String username, String password, String passwordConfirm, String institution, String group) {
        if (!password.equals(passwordConfirm)) {
            // TODO
            return;
        }

        api.createUser(username, password, institution, group)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getViewState().signIn(user.getUsername(), password),
                        throwable -> {
                            // TODO
                        });
    }
}
