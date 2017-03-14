package ru.visualmath.android.lecture;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;

public class LectureActivity extends MvpAppCompatActivity implements LectureView {

    @InjectPresenter
    LecturePresenter presenter;

    @BindView(R.id.pager)
    ViewPager pager;

    @ProvidePresenter
    LecturePresenter provideLecturePresenter() {
        return new LecturePresenter(getSupportFragmentManager());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            String lectureJson = getIntent().getStringExtra("lecture");
            Lecture lecture = new Gson().fromJson(lectureJson, Lecture.class);
            presenter.setLecture(lecture);
        }
    }

    @Override
    public void initViewPager(PagerAdapter pagerAdapter) {
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void showLecture(Lecture lecture) {
        pager.setOffscreenPageLimit(lecture.mapping.size());
    }
}
