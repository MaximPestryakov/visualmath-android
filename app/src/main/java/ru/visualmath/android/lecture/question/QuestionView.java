package ru.visualmath.android.lecture.question;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface QuestionView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startQuestion();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void finishQuestion();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void answerBlock();
}
