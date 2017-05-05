package ru.visualmath.android.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BindableViewHolder extends RecyclerView.ViewHolder {

    public BindableViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(int position);
}
