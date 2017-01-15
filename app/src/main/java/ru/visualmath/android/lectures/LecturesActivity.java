package ru.visualmath.android.lectures;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;

public class LecturesActivity extends MvpActivity<LecturesView, LecturesPresenter> implements LecturesView {

    @BindView(R.id.lectures_list)
    RecyclerView lecuresList;

    @BindView(R.id.loadingLectures)
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);
        ButterKnife.bind(this);

        presenter.loadLectures();
    }

    @NonNull
    @Override
    public LecturesPresenter createPresenter() {
        return new LecturesPresenter();
    }

    @Override
    public void showLoading() {
        lecuresList.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLectureList(List<Lecture> lectures) {
        loading.setVisibility(View.VISIBLE);

        lecuresList.setHasFixedSize(true);
        lecuresList.setLayoutManager(new LinearLayoutManager(this));
        lecuresList.setAdapter(new LecturesListAdapter(lectures));

        loading.setVisibility(View.GONE);
        lecuresList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        loading.setVisibility(View.GONE);
        lecuresList.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
