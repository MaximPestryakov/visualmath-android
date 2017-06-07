package ru.visualmath.android.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import ru.visualmath.android.R;

public class SignUpActivity extends MvpAppCompatActivity implements SignUpView {

    @InjectPresenter
    SignUpPresenter presenter;

    @BindView(R.id.username)
    EditText usernameEditText;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.passwordConfirm)
    EditText passwordConfirmEditText;

    @BindView(R.id.institution)
    EditText institutionEditText;

    @BindView(R.id.group)
    EditText groupEditText;

    @BindView(R.id.signUp)
    Button signUpButton;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        Observable.combineLatest(
                RxTextView.textChanges(usernameEditText),
                RxTextView.textChanges(passwordEditText),
                RxTextView.textChanges(passwordConfirmEditText),
                RxTextView.textChanges(institutionEditText),
                RxTextView.textChanges(groupEditText),
                (login, password, passwordConfirm, institution, group) -> login.length() > 0 &&
                        password.length() > 0 && passwordConfirm.length() > 0 &&
                        institution.length() > 0 && group.length() > 0
        ).subscribe(signUpButton::setEnabled);

        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordConfirmEditText.getText().toString();
            String institution = institutionEditText.getText().toString();
            String group = groupEditText.getText().toString();
            presenter.onSignUp(username, password, passwordConfirm, institution, group);
        });
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
    public void signIn(String username, String password) {
        Intent data = new Intent();
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(0, data);
        finish();
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.sign_in_error)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (d, id) -> presenter.onErrorCancel())
                .setOnCancelListener(d -> presenter.onErrorCancel());
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showError(@StringRes int messageId) {
        showError(getString(messageId));
    }

    @Override
    public void hideError() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
