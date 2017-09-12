package ru.visualmath.android.results;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Results;
import ru.visualmath.android.util.BindableViewHolder;

class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    @NonNull
    private final Results results;

    public ResultsAdapter(@NonNull Results results) {
        this.results = results;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return results.getAnswers().size();
    }

    class ResultViewHolder extends BindableViewHolder {

        ResultViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(int position) {

        }
    }
}
