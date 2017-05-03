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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Question;

public class QuestionFragment extends MvpAppCompatFragment implements QuestionView {

    public static final String TAG = "QuestionFragment";
    private static final String LECTURE_ID_KEY = "LECTURE_ID_KEY";
    private static final String QUESTION_ID_KEY = "QUESTION_ID_KEY";
    private static final String TITLE_KEY = "TITLE_KEY";
    private static final String ANSWERS_KEY = "ANSWERS_KEY";
    private static final String MULTIPLE_KEY = "MULTIPLE_KEY";

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
    private String questionId;
    private String question;
    private List<String> answers;
    private Boolean multiple;
    private QuestionAdapter adapter;

    public static QuestionFragment newInstance(String lectureId, Question question) {
        Bundle args = new Bundle();
        args.putString(LECTURE_ID_KEY, lectureId);
        args.putString(QUESTION_ID_KEY, question.getId());
        args.putString(TITLE_KEY, question.getTitle());
        args.putStringArrayList(ANSWERS_KEY, (ArrayList<String>) question.getAnswers());
        args.putBoolean(MULTIPLE_KEY, question.isMultiple());

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static QuestionFragment newInstance(String title, List<String> answers, Boolean multiple) {
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putStringArrayList(ANSWERS_KEY, (ArrayList<String>) answers);
        args.putBoolean(MULTIPLE_KEY, multiple);

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
            questionId = args.getString(QUESTION_ID_KEY);
            question = args.getString(TITLE_KEY);
            answers = args.getStringArrayList(ANSWERS_KEY);
            multiple = args.getBoolean(MULTIPLE_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_question, container, false);
        unbinder = ButterKnife.bind(this, view);

        questionTextView.setText(question);
        answersRecyclerView.setHasFixedSize(true);
        answersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter = new QuestionAdapter(answers, multiple);
        answersRecyclerView.setAdapter(adapter);

        answer.setOnClickListener(v -> presenter.onAnswer(lectureId, adapter.getAnswer(), questionId));

        skip.setOnClickListener(v -> presenter.onAnswer(lectureId, new ArrayList<>(), questionId));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
