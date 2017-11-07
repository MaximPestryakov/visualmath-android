package ru.visualmath.android.signup;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface SignUpView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void signIn(String username, String password);

    @StateStrategyType(SingleStateStrategy.class)
    void showError(String message);

    @StateStrategyType(SingleStateStrategy.class)
    void showError(@StringRes int messageId);

    @StateStrategyType(SingleStateStrategy.class)
    void hideError();
}
