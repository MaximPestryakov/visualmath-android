package ru.visualmath.android.lecture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;

public class LectureQuestionFragment extends Fragment {

    @BindView(R.id.lecture_question)
    TextView questionTextView;

    @BindView(R.id.lecture_answers)
    RecyclerView answersRecyclerView;

    private Unbinder unbinder;

    private String question;
    private List<String> answers;
    private Boolean multiple;

    private static final String QUESTION_KEY = "question";
    private static final String ANSWERS_KEY = "answers";
    private static final String MULTIPLE_KEY = "multiple";

    public static LectureQuestionFragment newInstance(String question, List<String> answers,
                                                      Boolean multiple) {
        Bundle args = new Bundle();
        args.putString(QUESTION_KEY, question);
        args.putStringArrayList(ANSWERS_KEY, (ArrayList<String>) answers);
        args.putBoolean(MULTIPLE_KEY, multiple);

        LectureQuestionFragment fragment = new LectureQuestionFragment();
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

    public static class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

        private List<String> answers;
        private Boolean multiple;
        private int radioButtonChosen = -1;

        public QuestionAdapter(List<String> answers, Boolean multiple) {
            this.answers = answers;
            this.multiple = multiple;
        }

        @Override
        public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lecture_question_item, parent, false);
            return new QuestionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(QuestionViewHolder holder, int position) {
            holder.answerCheckBox.setVisibility(multiple ? View.VISIBLE : View.INVISIBLE);
            holder.answerRadioButton.setVisibility(multiple ? View.INVISIBLE : View.VISIBLE);
            holder.answerRadioButton.setChecked(position == radioButtonChosen);
            holder.answerText.setText(answers.get(position));
        }

        @Override
        public int getItemCount() {
            return answers.size();
        }

        public class QuestionViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.answer_check_box)
            CheckBox answerCheckBox;

            @BindView(R.id.answer_radio_button)
            RadioButton answerRadioButton;

            @BindView(R.id.answer_text)
            TextView answerText;


            public QuestionViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                answerRadioButton.setOnClickListener(v -> {
                    radioButtonChosen = getAdapterPosition();
                    notifyItemRangeChanged(0, answers.size());
                });

            }
        }
    }
}
