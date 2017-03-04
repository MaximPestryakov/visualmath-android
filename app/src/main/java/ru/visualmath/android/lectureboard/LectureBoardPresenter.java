package ru.visualmath.android.lectureboard;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;

public class LectureBoardPresenter extends MvpBasePresenter<LectureBoardView> {

    void loadLectures() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        Log.d("LectureBoardPresenter", "loadLectures");

        VisualMathApi.getApi().lecturesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lectures -> {
                    if (isViewAttached()) {
                        getView().showLectureList(lectures);
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
