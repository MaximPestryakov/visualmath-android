package ru.visualmath.android.lecture.questionblock;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface QuestionBlockView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showQuestion();

    @StateStrategyType(SingleStateStrategy.class)
    void notStart();

    @StateStrategyType(SingleStateStrategy.class)
    void start();

    @StateStrategyType(SingleStateStrategy.class)
    void finish();
}
