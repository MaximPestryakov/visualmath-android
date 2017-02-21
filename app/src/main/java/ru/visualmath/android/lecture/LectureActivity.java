package ru.visualmath.android.lecture;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;

public class LectureActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;

    String lectureGson;

    Lecture lecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            lectureGson = savedInstanceState.getString("lecture_gson");
        } else {
            lectureGson = getIntent().getStringExtra("lecture");
        }
        lecture = new Gson().fromJson(lectureGson, Lecture.class);
        showLecture();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("lecture_gson", lectureGson);
    }

    /*
    @NonNull
    @Override
    public ViewState<LectureView> createViewState() {
        return new LectureViewState();
    }

    @NonNull
    @Override
    public LecturePresenter createPresenter() {
        return new LecturePresenter();
    }

    @Override
    public void onNewViewStateInstance() {
        showLecture();
    }
    */

    public void showLecture() {
        Log.d("LectureActivity", "showLecture");
        pager.setOffscreenPageLimit(lecture.mapping.size());
        LecturePageAdapter lecturePageAdapter = new LecturePageAdapter(getSupportFragmentManager(), lecture);
        pager.setAdapter(lecturePageAdapter);
    }
}
