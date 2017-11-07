package ru.visualmath.android.lectureboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.login.LoginActivity;

public class LectureBoardActivity extends MvpAppCompatActivity implements LectureBoardView {

    @InjectPresenter
    LectureBoardPresenter presenter;

    @BindView(R.id.lectures_list)
    RecyclerView lecturesList;

    @BindView(R.id.refresh_lectures_list)
    SwipeRefreshLayout refreshLecturesList;

    private LectureBoardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureboard);
        ButterKnife.bind(this);

        adapter = new LectureBoardAdapter(this);
        lecturesList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lecturesList.setLayoutManager(layoutManager);
        lecturesList.addItemDecoration(new DividerItemDecoration(lecturesList.getContext(),
                layoutManager.getOrientation()));
        lecturesList.setAdapter(adapter);

        refreshLecturesList.setOnRefreshListener(presenter::loadLectures);
    }

    @Override
    public void showLoading() {
        refreshLecturesList.setRefreshing(true);
    }

    @Override
    public void showLectureList(List<SyncLecture> syncLectures, List<Lecture> lectures) {
        refreshLecturesList.setRefreshing(true);
        adapter.setLectures(syncLectures, lectures);
        refreshLecturesList.setRefreshing(false);
    }

    @Override
    public void showError(int messageId) {
        refreshLecturesList.setRefreshing(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(messageId)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
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
