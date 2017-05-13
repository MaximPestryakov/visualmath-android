package ru.visualmath.android.synclecture;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlockSlide;


interface SyncLectureView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showModule(Module module);

    @StateStrategyType(SingleStateStrategy.class)
    void showQuestion(Question question, boolean isStarted);

    @StateStrategyType(SingleStateStrategy.class)
    void showQuestionBlock(QuestionBlockSlide slide);

    @StateStrategyType(SingleStateStrategy.class)
    void showFinish();
}
