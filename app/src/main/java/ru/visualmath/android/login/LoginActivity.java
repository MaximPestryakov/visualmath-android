package ru.visualmath.android.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.visualmath.android.R;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.email)
    EditText email;

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

        showLoginForm();
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoginForm() {
        setFormEnabled(true);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Log.d("LOGIN", "Error");

        setFormEnabled(true);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        setFormEnabled(false);
        loading.setVisibility(View.VISIBLE);
    }

    private void setFormEnabled(boolean enabled) {
        email.setEnabled(enabled);
        password.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }

    @Override
    public void loginSuccessful() {
        Log.d("LOGIN", "Successful");
        finish();
    }

    @OnClick(R.id.loginButton)
    public void onLoginClicked() {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        presenter.doLogin(emailValue, passwordValue);
    }
}
