package ru.visualmath.android.lectureboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.App;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.lectureboard.LectureBoardViewState.LectureState;
import ru.visualmath.android.login.LoginActivity;

public class LectureBoardActivity extends MvpViewStateActivity<LectureBoardView, LectureBoardPresenter> implements LectureBoardView {

    @BindView(R.id.lectures_list)
    RecyclerView lecturesList;

    @BindView(R.id.refresh_lectures_list)
    SwipeRefreshLayout refreshLecturesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);
        ButterKnife.bind(this);
        setRetainInstance(true);

        refreshLecturesList.setOnRefreshListener(presenter::loadLectures);
    }

    @NonNull
    @Override
    public LectureBoardPresenter createPresenter() {
        return new LectureBoardPresenter(App.from(this));
    }

    @NonNull
    @Override
    public ViewState<LectureBoardView> createViewState() {
        return new LectureBoardViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.loadLectures();
    }

    @Override
    public void showLoading() {
        setState(LectureState.SHOW_LOADING);
        lecturesList.setVisibility(View.GONE);
        refreshLecturesList.setRefreshing(true);
    }

    @Override
    public void showLectureList(List<Lecture> lectures) {
        setState(LectureState.SHOW_LECTURE_LIST, lectures);
        refreshLecturesList.setRefreshing(true);

        lecturesList.setHasFixedSize(true);
        lecturesList.setLayoutManager(new LinearLayoutManager(this));
        lecturesList.setAdapter(new LectureBoardListAdapter(this, lectures));

        refreshLecturesList.setRefreshing(false);
        lecturesList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        setState(LectureState.SHOW_ERROR, message);
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
        setState(LectureState.LOGOUT);
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    void setState(LectureState state) {
        ((LectureBoardViewState) viewState).setState(state);
    }


    void setState(LectureState state, Object data) {
        ((LectureBoardViewState) viewState).setState(state, data);
    }
}
