package ru.visualmath.android.lecture.questionblock;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.visualmath.android.api.model.Results;

interface QuestionBlockView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showQuestion();

    @StateStrategyType(SingleStateStrategy.class)
    void notStart();

    @StateStrategyType(SingleStateStrategy.class)
    void start();

    @StateStrategyType(SingleStateStrategy.class)
    void finishByUser();

    @StateStrategyType(SingleStateStrategy.class)
    void showResults(Results results);
}
