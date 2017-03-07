package ru.visualmath.android.lectureboard;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.SyncLecture;

interface LectureBoardView extends MvpView {

    void showLoading();

    void showLectureList(List<SyncLecture> syncLectures, List<Lecture> lectures);

    void showError(int messageId);

    void logout();
}
