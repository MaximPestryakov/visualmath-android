package ru.visualmath.android.lecture.question;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.VisualMathSync;
import ru.visualmath.android.api.model.QuestionAnswer;
import ru.visualmath.android.api.model.Stats;
import ru.visualmath.android.api.model.UserInfo;
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

    void onAnswer(String lectureId, List answer, String questionId) {
        if (blockId == null) {
            api.answerQuestion(lectureId, answer, questionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBody -> getViewState().showAnswered(), throwable ->
                            Log.d("MyTag", throwable.getMessage()));
        } else {
            api.answerBlock(lectureId, answer, blockId, questionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(answerRequest -> EventBus.getDefault().post(new NextQuestionEvent()), throwable ->
                            Log.d("MyTag", throwable.getMessage()));
        }
    }

    void getResults(String lectureId, String questionId) {
        api.userInfo(lectureId)
                .map(UserInfo::getQuestionAnswers)
                .map(questionAnswers -> questionAnswers.get(questionId))
                .map(QuestionAnswer::getStats)
                .map(Stats::getVotes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showStats, throwable ->
                        Log.d("MyTag", throwable.getMessage()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (syncApi != null) {
            syncApi.disconnect();
        }
    }
}
