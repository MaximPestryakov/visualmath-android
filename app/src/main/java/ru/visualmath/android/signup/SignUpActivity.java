package ru.visualmath.android.signup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import ru.visualmath.android.R;

public class SignUpActivity extends MvpAppCompatActivity implements MvpView {

    @InjectPresenter
    SignUpPresenter presenter;

    @BindView(R.id.loginValue)
    EditText loginValue;

    @BindView(R.id.password)
    EditText passwordValue;

    @BindView(R.id.retryPasswordValue)
    EditText retryPasswordValue;

    @BindView(R.id.institutionValue)
    EditText institutionValue;

    @BindView(R.id.groupValue)
    EditText groupValue;

    @BindView(R.id.signUp)
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @OnClick(R.id.signUp)
    void onSignUp() {
        String login = loginValue.getText().toString();
        String password = passwordValue.getText().toString();
        if (!password.equals(retryPasswordValue.getText().toString())) {
            // Incorrect passwords
            return;
        }
        String institution = institutionValue.getText().toString();
        String group = groupValue.getText().toString();
        presenter.onSignUp(login, password, institution, group);
    }
}
