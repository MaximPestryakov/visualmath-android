package ru.visualmath.android.results

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.maximpestryakov.katexview.KatexView
import ru.visualmath.android.R
import ru.visualmath.android.api.model.QuestionBlock
import ru.visualmath.android.api.model.Results
import ru.visualmath.android.util.BindableViewHolder

internal class ResultsAdapter(private val questionBlock: QuestionBlock, private val results: Results) : RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ResultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false))

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = results.answers.size

    internal inner class ResultViewHolder(itemView: View) : BindableViewHolder(itemView) {

        private val context = itemView.context
        private val question: KatexView = itemView.findViewById(R.id.question)
        private val mark: TextView = itemView.findViewById(R.id.mark)

        override fun bind(position: Int) {
            question.text = context.getString(R.string.results_question_title, position + 1, questionBlock.questions[position].title)
            mark.text = context.getString(R.string.results_question_mark, results.answers[position].mark)
        }
    }
}
