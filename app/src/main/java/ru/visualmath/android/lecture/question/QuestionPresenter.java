package ru.visualmath.android.lecture.question;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.VisualMathSync;
import ru.visualmath.android.event.NextQuestionEvent;

@InjectViewState
public class QuestionPresenter extends MvpPresenter<QuestionView> {

    private final VisualMathApi api = VisualMathApi.getApi();
    private String blockId;
    private VisualMathSync syncApi;

    void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    void connect(String lectureId, String questionId) {
        syncApi = new VisualMathSync.Builder(lectureId)
                .setOnStartQuestion(questionId, getViewState()::startQuestion)
                .setOnFinishQuestion(questionId, getViewState()::finishQuestion)
                .build();
        syncApi.connect();
    }

    void onAnswer(String lectureId, List<Integer> answer, String questionId) {
        if (blockId == null) {
            api.answerQuestion(lectureId, answer, questionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(answerRequest -> {
                    }, throwable -> {

                    });
        } else {
            api.answerBlock(lectureId, answer, blockId, questionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(answerRequest -> {
                        EventBus.getDefault().post(new NextQuestionEvent());
                    }, throwable -> {

                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (syncApi != null) {
            syncApi.disconnect();
        }
    }
}
