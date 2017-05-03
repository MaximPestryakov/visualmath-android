package ru.visualmath.android.lecture.question;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;


class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<String> answers;

    private Set<Integer> answer;

    private Boolean multiple;

    private QuestionViewHolder checked;

    QuestionAdapter(List<String> answers, Boolean multiple) {
        this.answers = answers;
        this.multiple = multiple;
        answer = new HashSet<>();
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lecture_question_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        if (multiple) {
            holder.answerCheckBox.setVisibility(View.VISIBLE);
            holder.answerRadioButton.setVisibility(View.INVISIBLE);
            holder.answerCheckBox.setChecked(answer.contains(position));
        } else {
            holder.answerCheckBox.setVisibility(View.INVISIBLE);
            holder.answerRadioButton.setVisibility(View.VISIBLE);
            if (answer.contains(position)) {
                checked = holder;
                holder.answerRadioButton.setChecked(true);
            } else {
                holder.answerRadioButton.setChecked(false);
            }
        }
        holder.answerText.setText(answers.get(position));
    }

    public List<Integer> getAnswer() {
        List<Integer> result = new ArrayList<>();
        result.addAll(answer);
        return result;
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.answer_check_box)
        CheckBox answerCheckBox;

        @BindView(R.id.answer_radio_button)
        RadioButton answerRadioButton;

        @BindView(R.id.answer_text)
        KatexView answerText;

        QuestionViewHolder(View itemView) {
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

            answerCheckBox.setOnCheckedChangeListener((v, isChecked) -> {
                if (isChecked) {
                    answer.add(getAdapterPosition());
                } else {
                    answer.remove(getAdapterPosition());
                }
            });
        }
    }
}
