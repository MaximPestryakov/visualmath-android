package ru.visualmath.android.lecture.question;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;
import ru.visualmath.android.util.BindableViewHolder;


class QuestionAdapter extends RecyclerView.Adapter<BindableViewHolder> {

    private List<String> answers;

    private SortedSet<Integer> answer;

    private Boolean multiple;

    private SingleQuestionViewHolder checked;

    QuestionAdapter(List<String> answers, Boolean multiple) {
        this.answers = answers;
        this.multiple = multiple;
        answer = new TreeSet<>();
    }

    @Override
    public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lecture_question_item, parent, false);

        ViewStub answerViewStub = ButterKnife.findById(view, R.id.answerViewStub);

        if (multiple) {
            answerViewStub.setLayoutResource(R.layout.layout_checkbox);
            answerViewStub.inflate();
            return new MultipleQuestionViewHolder(view);
        }

        answerViewStub.setLayoutResource(R.layout.layout_radio_button);
        answerViewStub.inflate();
        return new SingleQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BindableViewHolder holder, int position) {
        holder.bind(position);
    }

    List<Integer> getAnswer() {
        List<Integer> result = new ArrayList<>();
        result.addAll(answer);
        return result;
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class SingleQuestionViewHolder extends BindableViewHolder {

        @BindView(R.id.answerCompoundButton)
        RadioButton answerRadioButton;

        @BindView(R.id.answer_text)
        KatexView answerText;

        SingleQuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            answerRadioButton.setOnCheckedChangeListener((v, isChecked) -> {
                if (isChecked) {
                    answer.clear();
                    answer.add(getAdapterPosition());
                    if (checked != null) {
                        checked.answerRadioButton.setChecked(false);
                    }
                    checked = this;
                }
            });
        }

        @Override
        public void bind(int position) {
            if (answer.contains(position)) {
                checked = this;
                answerRadioButton.setChecked(true);
            } else {
                answerRadioButton.setChecked(false);
            }
            answerText.setText(answers.get(position));
        }
    }

    class MultipleQuestionViewHolder extends BindableViewHolder {

        @BindView(R.id.answerCompoundButton)
        CheckBox answerCheckBox;

        @BindView(R.id.answer_text)
        KatexView answerText;

        MultipleQuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            answerCheckBox.setOnCheckedChangeListener((v, isChecked) -> {
                if (isChecked) {
                    answer.add(getAdapterPosition());
                } else {
                    answer.remove(getAdapterPosition());
                }
            });
        }

        @Override
        public void bind(int position) {
            answerCheckBox.setChecked(answer.contains(position));
            answerText.setText(answers.get(position));
        }
    }
}
