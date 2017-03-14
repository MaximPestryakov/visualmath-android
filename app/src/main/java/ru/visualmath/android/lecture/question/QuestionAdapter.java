package ru.visualmath.android.lecture.question;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;


class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<String> answers;
    private Boolean multiple;
    private int radioButtonChosen = -1;

    QuestionAdapter(List<String> answers, Boolean multiple) {
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

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.answer_check_box)
        CheckBox answerCheckBox;

        @BindView(R.id.answer_radio_button)
        RadioButton answerRadioButton;

        @BindView(R.id.answer_text)
        TextView answerText;


        QuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            answerRadioButton.setOnClickListener(v -> {
                radioButtonChosen = getAdapterPosition();
                notifyItemRangeChanged(0, answers.size());
            });
        }
    }
}