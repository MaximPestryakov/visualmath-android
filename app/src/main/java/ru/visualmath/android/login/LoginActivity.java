package ru.visualmath.android.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.visualmath.android.App;
import ru.visualmath.android.R;
import ru.visualmath.android.lectureboard.LectureBoardActivity;
import ru.visualmath.android.login.LoginViewState.LoginState;

public class LoginActivity extends MvpViewStateActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.loading)
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(App.from(this));
    }

    @NonNull
    @Override
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showLoginForm();
    }

    @Override
    public void showLoginForm() {
        setState(LoginState.SHOW_LOGIN_FORM);
        setFormEnabled(true);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        setState(LoginState.SHOW_ERROR, message);
        Log.d("LOGIN", "Error");

        setFormEnabled(true);
        loading.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ошибка входа")
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showLoading() {
        setState(LoginState.SHOW_LOADING);
        setFormEnabled(false);
        loading.setVisibility(View.VISIBLE);
    }

    private void setFormEnabled(boolean enabled) {
        name.setEnabled(enabled);
        password.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }

    @Override
    public void loginSuccessful() {
        setState(LoginState.LOGIN_SUCCESSFUL);
        Intent intent = new Intent(this, LectureBoardActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClicked() {
        String nameValue = name.getText().toString();
        String passwordValue = password.getText().toString();
        presenter.doLogin(nameValue, passwordValue);
    }

    void setState(LoginState state) {
        ((LoginViewState) viewState).setState(state);
    }


    void setState(LoginState state, Object data) {
        ((LoginViewState) viewState).setState(state, data);
    }
}
