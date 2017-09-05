package ru.visualmath.android.lectureboard;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
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

        Single.concat(api.syncLecturesList(), api.lecturesList())
                .toList()
                .map(lists -> new Pair<>((List<SyncLecture>) lists.get(0), (List<Lecture>) lists.get(1)))
                .map(lecturesPair -> {
                    Collections.sort(lecturesPair.first, (l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()));

                    for (int i = 0; i < lecturesPair.second.size(); ++i) {
                        if (lecturesPair.second.get(i).isHidden()) {
                            lecturesPair.second.remove(i);
                        }
                    }
                    Collections.sort(lecturesPair.second, (l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()));

                    return lecturesPair;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lecturePair ->
                        getViewState().showLectureList(lecturePair.first, lecturePair.second), throwable -> {
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
