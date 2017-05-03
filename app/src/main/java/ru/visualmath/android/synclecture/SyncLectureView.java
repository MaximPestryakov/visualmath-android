package ru.visualmath.android.synclecture;

import com.arellomobile.mvp.MvpView;

import ru.visualmath.android.api.model.Question;


public interface SyncLectureView extends MvpView {

    void showModule(String name, String content);

    void showQuestion(Question question);
}
