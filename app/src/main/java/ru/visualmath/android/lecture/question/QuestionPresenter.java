package ru.visualmath.android.lecture.question;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.VisualMathSync;

@InjectViewState
public class QuestionPresenter extends MvpPresenter<QuestionView> {

    private VisualMathApi api = VisualMathApi.getApi();

    private VisualMathSync syncApi;

    void connect(String lectureId, String questionId) {
        syncApi = new VisualMathSync.Builder(lectureId)
                .setOnStartQuestion(questionId, () -> getViewState().startQuestion())
                .setOnFinishQuestion(questionId, () -> getViewState().finishQuestion())
                .build();
        syncApi.connect();
    }

    void onAnswer(String lectureId, List<Integer> answer, String questionId) {
        api.answerQuestion(lectureId, answer, questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answerRequest -> {

                }, throwable -> {

                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        syncApi.disconnect();
    }
}
