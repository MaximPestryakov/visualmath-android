package ru.visualmath.android.lecture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;

public class LectureActivity extends MvpAppCompatActivity implements LectureView {

    private static final String EXTRA_LECTURE = "EXTRA_LECTURE";
    @InjectPresenter
    LecturePresenter presenter;
    @BindView(R.id.pager)
    ViewPager pager;

    public static Intent getStartIntent(Context context, Lecture lecture) {
        Intent intent = new Intent(context, LectureActivity.class);
        intent.putExtra(EXTRA_LECTURE, lecture);
        return intent;
    }

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
            Lecture lecture = (Lecture) getIntent().getSerializableExtra(EXTRA_LECTURE);
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
