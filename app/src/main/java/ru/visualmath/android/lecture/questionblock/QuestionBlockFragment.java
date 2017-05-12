package ru.visualmath.android.lecture.questionblock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.lecture.question.QuestionFragment;

public class QuestionBlockFragment extends MvpAppCompatFragment implements QuestionBlockView {

    public static final String TAG = "QuestionBlockFragment";

    private static final String ARGUMENT_LECTURE_ID = "ARGUMENT_LECTURE_ID";
    private static final String ARGUMENT_QUESTION_BLOCK = "ARGUMENT_QUESTION_BLOCK";
    private static final String ARGUMENT_INDEX = "ARGUMENT_INDEX";
    private static final String ARGUMENT_IS_STARTED = "ARGUMENT_IS_STARTED";
    @InjectPresenter
    QuestionBlockPresenter presenter;
    private String lectureId;
    private QuestionBlock questionBlock;
    private List<Question> questions;
    private boolean isStarted;
    private int index;

    public static QuestionBlockFragment newInstance(String lectureId, QuestionBlock questionBlock, int index, boolean isStarted) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_LECTURE_ID, lectureId);
        args.putSerializable(ARGUMENT_QUESTION_BLOCK, questionBlock);
        args.putInt(ARGUMENT_INDEX, index);
        args.putBoolean(ARGUMENT_IS_STARTED, isStarted);

        QuestionBlockFragment fragment = new QuestionBlockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            lectureId = args.getString(ARGUMENT_LECTURE_ID);
            questionBlock = (QuestionBlock) args.getSerializable(ARGUMENT_QUESTION_BLOCK);
            questions = questionBlock.getQuestions();
            index = args.getInt(ARGUMENT_INDEX);
            isStarted = args.getBoolean(ARGUMENT_IS_STARTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_block, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter.connect(lectureId, questionBlock.getId());
        if (isStarted) {
            presenter.onStart();
        }
    }

    @Override
    public void showQuestion() {
        Log.d("MyTag", "index: " + index);
        if (index < 0 || index >= questions.size()) {
            // done
            return;
        }
        QuestionFragment fragment = QuestionFragment.newInstance(lectureId, questions.get(index++), questionBlock.getId());

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.questionBlock, fragment, QuestionFragment.TAG)
                .commit();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }
}
