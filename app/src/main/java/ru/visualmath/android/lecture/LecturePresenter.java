package ru.visualmath.android.lecture;


import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.visualmath.android.api.model.Lecture;

@InjectViewState
public class LecturePresenter extends MvpPresenter<LectureView> {

    private LecturePageAdapter lecturePageAdapter;

    LecturePresenter(FragmentManager fm) {
        lecturePageAdapter = new LecturePageAdapter(fm);
        getViewState().initViewPager(lecturePageAdapter);
    }

    void setLecture(Lecture lecture) {
        lecturePageAdapter.setLecture(lecture);
        getViewState().showLecture(lecture);
    }
}
