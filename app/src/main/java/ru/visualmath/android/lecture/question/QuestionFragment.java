package ru.visualmath.android.lecture.question;

import com.example.android.paint.PaintActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.paint.PaintActivity;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Question;

import static android.app.Activity.RESULT_OK;

public class QuestionFragment extends MvpAppCompatFragment implements QuestionView {

    public static final String TAG = "QuestionFragment";

    private static final String ARGUMENT_LECTURE_ID = "ARGUMENT_LECTURE_ID";
    private static final String ARGUMENT_QUESTION = "ARGUMENT_QUESTION";
    private static final String ARGUMENT_IS_STARTED = "ARGUMENT_IS_STARTED";
    private static final String ARGUMENT_BLOCK_ID = "ARGUMENT_BLOCK_ID";
    private static final int DRAW_RESULT_REQUEST_CODE = 1;


    @BindView(R.id.lecture_question)
    KatexView questionTextView;
    @BindView(R.id.lecture_answers)
    RecyclerView answersRecyclerView;
    @BindView(R.id.symbolicAnswer)
    EditText symbolicAnswer;
    @BindView(R.id.draw_answer)
    Button drawAnswer;
    @BindView(R.id.obtained_result)
    KatexView katexPreview;
    @BindView(R.id.skip)
    Button skip;
    @BindView(R.id.answer)
    Button answer;
    @InjectPresenter

    QuestionPresenter presenter;
    private Unbinder unbinder;
    private String lectureId;
    private Question question;
    private boolean isStarted;
    private String blockId;
    private QuestionAdapter adapter;

    public static QuestionFragment newInstance(String lectureId, Question question, boolean isStarted) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_LECTURE_ID, lectureId);
        args.putSerializable(ARGUMENT_QUESTION, question);
        args.putBoolean(ARGUMENT_IS_STARTED, isStarted);

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static QuestionFragment newInstance(String lectureId, Question question, String blockId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_LECTURE_ID, lectureId);
        args.putSerializable(ARGUMENT_QUESTION, question);
        args.putBoolean(ARGUMENT_IS_STARTED, true);
        args.putString(ARGUMENT_BLOCK_ID, blockId);


        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            lectureId = args.getString(ARGUMENT_LECTURE_ID);
            question = (Question) args.getSerializable(ARGUMENT_QUESTION);
            isStarted = args.getBoolean(ARGUMENT_IS_STARTED);
            blockId = args.getString(ARGUMENT_BLOCK_ID);
        }
        presenter.setBlockId(blockId);
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

        if (question.isSymbolic()) {
            answersRecyclerView.setVisibility(View.GONE);
            symbolicAnswer.setVisibility(View.VISIBLE);
            drawAnswer.setVisibility(View.VISIBLE);
            Intent drawIntent = new Intent(getActivity(), PaintActivity.class);
            drawAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivityForResult(drawIntent, DRAW_RESULT_REQUEST_CODE);
                }
            });
            katexPreview.setVisibility(View.VISIBLE);

            answer.setOnClickListener(v -> presenter.onAnswer(lectureId,
                    Collections.singletonList(symbolicAnswer.getText().toString()), question.getId()));
        } else {
            answersRecyclerView.setVisibility(View.VISIBLE);
            symbolicAnswer.setVisibility(View.GONE);

            answersRecyclerView.setHasFixedSize(true);
            answersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new QuestionAdapter(question.getAnswers(), question.isMultiple());
            answersRecyclerView.setAdapter(adapter);
            answer.setOnClickListener(v -> presenter.onAnswer(lectureId, adapter.getAnswer(), question.getId()));
        }

        questionTextView.setText(question.getTitle());

        skip.setOnClickListener(v -> presenter.onAnswer(lectureId, new ArrayList<>(), question.getId()));
        if (blockId == null) {
            presenter.connect(lectureId, question.getId());
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String returnedResult = data.getData().toString();
        katexPreview.setText(returnedResult);
    }
}
