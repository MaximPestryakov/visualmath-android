package ru.visualmath.android.lectureboard;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.App;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.SyncLecture;

@InjectViewState
public class LectureBoardPresenter extends MvpPresenter<LectureBoardView> {

    private App app;

    LectureBoardPresenter(App app) {
        this.app = app;
        loadLectures();
    }

    void loadLectures() {
        getViewState().showLoading();

        List<SyncLecture> syncLectures = new ArrayList<>();
        List<Lecture> lectures = new ArrayList<>();

        Observable.concat(app.getApi().syncLecturesList(), app.getApi().lecturesList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allLectures -> {
                    if (!allLectures.isEmpty() && allLectures.get(0) instanceof SyncLecture) {
                        syncLectures.addAll((List<SyncLecture>) allLectures);
                        syncLectures.sort((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()));
                    } else if (!allLectures.isEmpty() && allLectures.get(0) instanceof Lecture) {
                        lectures.addAll((List<Lecture>) allLectures);
                        lectures.removeIf(lecture -> lecture.hidden);
                        lectures.sort((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()));
                        getViewState().showLectureList(syncLectures, lectures);
                        Log.d("SMTH", "SMTH");
                    }
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        if (((HttpException) throwable).code() == 500) {
                            getViewState().logout();
                        } else {
                            getViewState().showError(R.string.server_is_not_available);
                        }
                    } else if (throwable instanceof IOException) {
                        getViewState().showError(R.string.check_the_internet_connection);
                    } else {
                        getViewState().showError(R.string.unknown_error);
                    }
                });
    }
}
