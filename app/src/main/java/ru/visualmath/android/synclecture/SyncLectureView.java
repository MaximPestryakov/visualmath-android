package ru.visualmath.android.synclecture;

import com.arellomobile.mvp.MvpView;


public interface SyncLectureView extends MvpView {

    void showModule(String name, String content);
}
