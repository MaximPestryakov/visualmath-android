package ru.visualmath.android.synclecture;

import android.os.Bundle;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;

public class SyncLectureActivity extends MvpAppCompatActivity implements SyncLectureView {

    @InjectPresenter
    SyncLecturePresenter presenter;

    @BindView(R.id.testing)
    TextView testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_lecture);
        ButterKnife.bind(this);

        String ongoingId = getIntent().getStringExtra("ongoing_id");

        presenter.connect(ongoingId);
    }

    @Override
    public void show(String message) {
        testing.setText(message);
    }
}
