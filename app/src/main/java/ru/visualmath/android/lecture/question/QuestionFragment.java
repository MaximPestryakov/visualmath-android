package ru.visualmath.android.lecture.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Question;

public class QuestionFragment extends MvpAppCompatFragment implements QuestionView {

    public static final String TAG = "QuestionFragment";
    private static final String LECTURE_ID_KEY = "LECTURE_ID_KEY";
    private static final String QUESTION_KEY = "QUESTION_KEY";
    private static final String IS_STARTED_KEY = "IS_STARTED_KEY";

    @BindView(R.id.lecture_question)
    KatexView questionTextView;

    @BindView(R.id.lecture_answers)
    RecyclerView answersRecyclerView;

    @BindView(R.id.answer)
    Button answer;

    @BindView(R.id.skip)
    Button skip;

    @InjectPresenter
    QuestionPresenter presenter;

    private Unbinder unbinder;
    private String lectureId;
    private Question question;
    private boolean isStarted;
    private QuestionAdapter adapter;

    public static QuestionFragment newInstance(String lectureId, Question question, boolean isStarted) {
        Bundle args = new Bundle();
        args.putString(LECTURE_ID_KEY, lectureId);
        args.putSerializable(QUESTION_KEY, question);
        args.putBoolean(IS_STARTED_KEY, isStarted);

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            lectureId = args.getString(LECTURE_ID_KEY);
            question = (Question) args.getSerializable(QUESTION_KEY);
            isStarted = args.getBoolean(IS_STARTED_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_question, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (isStarted) {
            startQuestion();
        } else {
            finishQuestion();
        }

        questionTextView.setText(question.getTitle());
        answersRecyclerView.setHasFixedSize(true);
        answersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter = new QuestionAdapter(question.getAnswers(), question.isMultiple());
        answersRecyclerView.setAdapter(adapter);

        answer.setOnClickListener(v -> presenter.onAnswer(lectureId, adapter.getAnswer(), question.getId()));

        skip.setOnClickListener(v -> presenter.onAnswer(lectureId, new ArrayList<>(), question.getId()));

        presenter.connect(lectureId, question.getId());
        return view;
    }

    @Override
    public void startQuestion() {
        answer.setVisibility(View.VISIBLE);
        skip.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishQuestion() {
        answer.setVisibility(View.GONE);
        skip.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
