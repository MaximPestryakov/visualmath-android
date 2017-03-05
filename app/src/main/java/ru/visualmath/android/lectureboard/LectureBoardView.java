package ru.visualmath.android.lectureboard;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import ru.visualmath.android.api.model.Lecture;

interface LectureBoardView extends MvpView {

    void showLoading();

    void showLectureList(List<Lecture> lectures);

    void showError(String message);

    void logout();
}
