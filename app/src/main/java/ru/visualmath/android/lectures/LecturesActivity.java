package ru.visualmath.android.lectures;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.login.LoginActivity;

public class LecturesActivity extends MvpActivity<LecturesView, LecturesPresenter> implements LecturesView {

    @BindView(R.id.lectures_list)
    RecyclerView lecturesList;

    @BindView(R.id.refresh_lectures_list)
    SwipeRefreshLayout refreshLecturesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);
        ButterKnife.bind(this);

        refreshLecturesList.setOnRefreshListener(() -> presenter.loadLectures());
        presenter.loadLectures();
    }

    @NonNull
    @Override
    public LecturesPresenter createPresenter() {
        return new LecturesPresenter();
    }

    @Override
    public void showLoading() {
        lecturesList.setVisibility(View.GONE);
        refreshLecturesList.setRefreshing(true);
    }

    @Override
    public void showLectureList(List<Lecture> lectures) {
        refreshLecturesList.setRefreshing(true);

        lecturesList.setHasFixedSize(true);
        lecturesList.setLayoutManager(new LinearLayoutManager(this));
        lecturesList.setAdapter(new LecturesListAdapter(lectures));

        refreshLecturesList.setRefreshing(false);
        lecturesList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        lecturesList.setVisibility(View.GONE);
        refreshLecturesList.setRefreshing(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }
}
