package ru.visualmath.android.lectureboard;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.App;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.SyncLecture;

class LectureBoardPresenter extends MvpBasePresenter<LectureBoardView> {

    private App app;

    LectureBoardPresenter(App app) {
        this.app = app;
    }

    void loadLectures() {
        if (isViewAttached()) {
            getView().showLoading();
        }

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
                        getView().showLectureList(syncLectures, lectures);
                    }
                }, throwable -> {
                    String errorMessage;
                    if (throwable instanceof HttpException) {
                        if (((HttpException) throwable).code() == 500) {
                            if (isViewAttached()) {
                                getView().logout();
                            }
                            return;
                        } else {
                            errorMessage = "Сервер временно недоступен";
                        }
                    } else if (throwable instanceof IOException) {
                        errorMessage = "Проверьте подключение к интернету";
                    } else {
                        errorMessage = "Неизвестная ошибка";
                    }
                    Log.d("Error", throwable.getMessage());
                    if (isViewAttached()) {
                        getView().showError(errorMessage);
                    }
                });
    }
}
