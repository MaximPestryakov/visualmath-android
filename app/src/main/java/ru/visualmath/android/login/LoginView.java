package ru.visualmath.android.login;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface LoginView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showLoginForm();

    @StateStrategyType(SingleStateStrategy.class)
    void showError(int messageId);

    @StateStrategyType(SingleStateStrategy.class)
    void showLoading();

    @StateStrategyType(SingleStateStrategy.class)
    void loginSuccessful();

    @StateStrategyType(SingleStateStrategy.class)
    void hideError();
}
