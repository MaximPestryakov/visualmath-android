package ru.visualmath.android.synclecture;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.visualmath.android.api.model.Question;


interface SyncLectureView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showModule(String name, String content);

    @StateStrategyType(SingleStateStrategy.class)
    void showQuestion(Question question, boolean isStarted);
}
