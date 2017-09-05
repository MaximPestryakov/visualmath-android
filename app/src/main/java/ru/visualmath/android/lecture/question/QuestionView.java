package ru.visualmath.android.lecture.question;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.visualmath.android.api.model.Stats;

interface QuestionView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startQuestion();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void finishQuestion();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAnswered();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showStats(List<Integer> votes);
}
