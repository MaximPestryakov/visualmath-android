package ru.visualmath.android.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.visualmath.android.R;
import ru.visualmath.android.lectureboard.LectureBoardActivity;
import ru.visualmath.android.signup.SignUpActivity;

public class LoginActivity extends MvpAppCompatActivity implements LoginView {

    @InjectPresenter
    LoginPresenter presenter;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.signUpButton)
    TextView signUpButton;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.setOnCancelListener(null);
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void showLoginForm() {
        setFormEnabled(true);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showError(int messageId) {
        setFormEnabled(true);
        loading.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.sign_in_error)
                .setMessage(messageId)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (d, id) -> presenter.onErrorCancel())
                .setOnCancelListener(d -> presenter.onErrorCancel());
        dialog = builder.create();
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
        Intent intent = new Intent(this, LectureBoardActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void hideError() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @OnClick(R.id.loginButton)
    public void onLoginClicked() {
        String nameValue = name.getText().toString();
        String passwordValue = password.getText().toString();
        presenter.onLogin(nameValue, passwordValue);
    }

    @OnClick(R.id.signUpButton)
    public void onSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
