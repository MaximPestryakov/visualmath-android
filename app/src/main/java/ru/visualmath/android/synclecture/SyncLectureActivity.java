package ru.visualmath.android.synclecture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.lecture.module.ModuleFragment;
import ru.visualmath.android.lecture.question.QuestionFragment;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SyncLectureActivity extends MvpAppCompatActivity implements SyncLectureView {

    private static final String LECTURE_ID = "LECTURE_ID";
    @InjectPresenter
    SyncLecturePresenter presenter;
    private String lectureId;

    public static Intent getStartIntent(Context context, String lectureId) {
        return new Intent(context, SyncLectureActivity.class).putExtra(LECTURE_ID, lectureId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.frameLayout);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        setContentView(frameLayout);

        lectureId = getIntent().getStringExtra(LECTURE_ID);
        presenter.connect(lectureId);
    }

    @Override
    public void showModule(String name, String content) {
        Fragment fragment = ModuleFragment.newInstance(name, content);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, ModuleFragment.TAG)
                .commit();
    }

    @Override
    public void showQuestion(Question question) {
        Fragment fragment = QuestionFragment.newInstance(lectureId, question);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, QuestionFragment.TAG)
                .commit();
    }
}
