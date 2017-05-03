package ru.visualmath.android.lecture.question;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;

@InjectViewState
public class QuestionPresenter extends MvpPresenter<QuestionView> {

    private VisualMathApi api = VisualMathApi.getApi();

    void onAnswer(String lectureId, List<Integer> answer, String questionId) {
        api.answerQuestion(lectureId, answer, questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answerRequest -> {

                }, throwable -> {

                });
    }
}
