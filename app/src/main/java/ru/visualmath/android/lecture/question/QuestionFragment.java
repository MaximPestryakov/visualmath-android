package ru.visualmath.android.lecture.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;

public class QuestionFragment extends Fragment {

    private static final String QUESTION_KEY = "question";
    private static final String ANSWERS_KEY = "answers";
    private static final String MULTIPLE_KEY = "multiple";
    @BindView(R.id.lecture_question)
    TextView questionTextView;
    @BindView(R.id.lecture_answers)
    RecyclerView answersRecyclerView;
    private Unbinder unbinder;
    private String question;
    private List<String> answers;
    private Boolean multiple;

    public static QuestionFragment newInstance(String question, List<String> answers,
                                               Boolean multiple) {
        Bundle args = new Bundle();
        args.putString(QUESTION_KEY, question);
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
            question = args.getString(QUESTION_KEY);
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
                super.canScrollVertically();
                return false;
            }
        });
        answersRecyclerView.setAdapter(new QuestionAdapter(answers, multiple));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
