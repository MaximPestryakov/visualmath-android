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

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.visualmath.android.R;
import ru.visualmath.android.lectures.LecturesActivity;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

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
    public void showError(String message) {
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
        Intent intent = new Intent(this, LecturesActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClicked() {
        String nameValue = name.getText().toString();
        String passwordValue = password.getText().toString();
        presenter.doLogin(nameValue, passwordValue);
    }
}
