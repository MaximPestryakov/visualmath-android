package ru.visualmath.android.lecture.questionblock;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.visualmath.android.api.VisualMathSync;
import ru.visualmath.android.event.NextQuestionEvent;

@InjectViewState
public class QuestionBlockPresenter extends MvpPresenter<QuestionBlockView> {

    private final EventBus eventBus = EventBus.getDefault();

    private VisualMathSync syncApi;

    QuestionBlockPresenter() {
        eventBus.register(this);
    }

    void connect(String lectureId, String blockId) {
        syncApi = new VisualMathSync.Builder(lectureId)
                .setOnStartBlock(blockId, getViewState()::start)
                .setOnFinishBlock(blockId, getViewState()::finish)
                .build();
        syncApi.connect();
    }

    void onStart() {
        getViewState().start();
        getViewState().showQuestion();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextQuestion(NextQuestionEvent event) {
        getViewState().showQuestion();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        syncApi.disconnect();
        eventBus.unregister(this);
        Log.d("MyTag", "onDestroy");
    }
}
