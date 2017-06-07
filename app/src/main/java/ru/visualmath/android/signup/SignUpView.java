package ru.visualmath.android.signup;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface SignUpView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void signIn(String username, String password);
}
