package ru.visualmath.android.lectures;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import ru.visualmath.android.api.model.Lecture;

interface LecturesView extends MvpView {

    void showLoading();

    void showLectureList(List<Lecture> lectures);

    void showError(String message);

    void logout();
}
