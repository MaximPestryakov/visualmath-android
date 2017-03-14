package ru.visualmath.android.lecture;

import android.support.v4.view.PagerAdapter;

import com.arellomobile.mvp.MvpView;

import ru.visualmath.android.api.model.Lecture;

interface LectureView extends MvpView {

    void initViewPager(PagerAdapter pagerAdapter);

    void showLecture(Lecture lecture);
}
