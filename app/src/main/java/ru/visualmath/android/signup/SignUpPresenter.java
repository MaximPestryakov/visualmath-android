package ru.visualmath.android.signup;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import ru.visualmath.android.R;
import ru.visualmath.android.api.VisualMathApi;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<SignUpView> {

    private final VisualMathApi api = VisualMathApi.getApi();

    void onSignUp(String username, String password, String passwordConfirm, String institution, String group) {
        if (!password.equals(passwordConfirm)) {
            getViewState().showError("Пароли не совпадают!");
            return;
        }

        api.createUser(username, password, institution, group)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getViewState().signIn(user.getUsername(), password),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 500) {
                                    ResponseBody errorBody = httpException.response().errorBody();
                                    if (errorBody != null) {
                                        String message = errorBody.string().trim();
                                        if (message.startsWith("\"")) {
                                            message = message.substring(1);
                                        }
                                        if (message.endsWith("\"")) {
                                            message = message.substring(0, message.length() - 1);
                                        }
                                        getViewState().showError(message);
                                    } else {
                                        getViewState().showError(R.string.unknown_error);
                                    }
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
