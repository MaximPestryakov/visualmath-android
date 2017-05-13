package ru.visualmath.android.synclecture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.QuestionBlockSlide;
import ru.visualmath.android.lecture.module.ModuleFragment;
import ru.visualmath.android.lecture.question.QuestionFragment;
import ru.visualmath.android.lecture.questionblock.QuestionBlockFragment;
import ru.visualmath.android.message.MessageFragment;
import ru.visualmath.android.util.FragmentUtil;

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
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showModule(Module module) {
        String tag = ModuleFragment.TAG + "$" + module.getId();
        FragmentUtil.showFragment(getSupportFragmentManager(), R.id.frameLayout, tag,
                v -> ModuleFragment.newInstance(module.getName(), module.getContent()));
    }

    @Override
    public void showQuestion(Question question, boolean isStarted) {
        String tag = QuestionFragment.TAG + "$" + question.getId();
        FragmentUtil.showFragment(getSupportFragmentManager(), R.id.frameLayout, tag,
                v -> QuestionFragment.newInstance(lectureId, question, isStarted));
    }

    @Override
    public void showQuestionBlock(QuestionBlockSlide slide) {
        QuestionBlock questionBlock = slide.getQuestionBlock();
        String tag = QuestionBlockFragment.TAG + "$" + questionBlock.getId();
        FragmentUtil.showFragment(getSupportFragmentManager(), R.id.frameLayout, tag,
                v -> QuestionBlockFragment.newInstance(lectureId, questionBlock, slide.getIndex(), slide.isStarted()));
    }

    @Override
    public void showFinish() {
        String tag = MessageFragment.TAG + "$" + R.string.message_lecture_finished;
        FragmentUtil.showFragment(getSupportFragmentManager(), R.id.frameLayout, tag,
                v -> MessageFragment.newInstance(R.string.message_lecture_finished));
    }
}
