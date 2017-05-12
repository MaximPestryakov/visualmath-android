package ru.visualmath.android.lectureboard;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import ru.visualmath.android.R;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.SyncLecture;

@InjectViewState
public class LectureBoardPresenter extends MvpPresenter<LectureBoardView> {

    private VisualMathApi api;

    LectureBoardPresenter() {
        api = VisualMathApi.getApi();
        loadLectures();
    }

    void loadLectures() {
        getViewState().showLoading();

        List<SyncLecture> syncLectures = new ArrayList<>();
        List<Lecture> lectures = new ArrayList<>();

        Observable.concat(api.syncLecturesList(), api.lecturesList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allLectures -> {
                    if (!allLectures.isEmpty() && allLectures.get(0) instanceof SyncLecture) {
                        syncLectures.addAll((List<SyncLecture>) allLectures);
                        Collections.sort(syncLectures, (l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()));
                    } else if (!allLectures.isEmpty() && allLectures.get(0) instanceof Lecture) {
                        for (Lecture lecture : (List<Lecture>) allLectures) {
                            if (!lecture.hidden) {
                                lectures.add(lecture);
                            }
                        }
                        Collections.sort(lectures, (l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()));
                        getViewState().showLectureList(syncLectures, lectures);
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
                        // Log.d("Error", throwable.getStackTrace().toString());
                        throwable.printStackTrace();
                    }
                });
    }
}
