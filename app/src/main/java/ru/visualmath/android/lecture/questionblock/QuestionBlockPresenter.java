package ru.visualmath.android.lecture.questionblock;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.VisualMathSync;
import ru.visualmath.android.api.model.Block;
import ru.visualmath.android.api.model.UserInfo;
import ru.visualmath.android.event.NextQuestionEvent;

@InjectViewState
public class QuestionBlockPresenter extends MvpPresenter<QuestionBlockView> {

    private final EventBus eventBus = EventBus.getDefault();
    private final VisualMathApi api = VisualMathApi.getApi();

    private VisualMathSync syncApi;

    QuestionBlockPresenter() {
        eventBus.register(this);
    }

    void connect(String lectureId, String blockId) {
        syncApi = new VisualMathSync.Builder(lectureId)
                .setOnStartBlock(blockId, this::onStart)
                .setOnFinishBlock(blockId, () -> loadResults(lectureId, blockId))
                .build();
        syncApi.connect();
    }

    void onNotStart() {
        getViewState().notStart();
    }

    void onStart() {
        getViewState().start();
        getViewState().showQuestion();
    }

    void onFinish() {
        getViewState().finishByUser();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextQuestion(NextQuestionEvent event) {
        getViewState().showQuestion();
    }

    private void loadResults(String lectureId, String blockId) {
        api.userInfo(lectureId)
                .map(UserInfo::getBlocks)
                .map(blocks -> blocks.get(blockId))
                .map(Block::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showResults, Throwable::printStackTrace);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        syncApi.disconnect();
        eventBus.unregister(this);
        Log.d("MyTag", "onDestroy");
    }
}
