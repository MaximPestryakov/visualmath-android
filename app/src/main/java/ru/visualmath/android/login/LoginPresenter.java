package ru.visualmath.android.login;

import android.os.AsyncTask;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public void doLogin(String email, String password) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        Log.d("LOGIN", email + " " + password);

        //call api
        new SomeAsyncLogic(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().loginSuccessful();
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().showError();
                }
            }
        }).execute();
    }
}

// emulating async
class SomeAsyncLogic extends AsyncTask<Void, Void, Boolean> {

    Runnable onSuccess;
    Runnable onError;

    SomeAsyncLogic(Runnable onSuccess, Runnable onError) {
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        return Math.random() < 0.5;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            onSuccess.run();
        } else {
            onError.run();
        }
    }
}