package ru.visualmath.android.lectureboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.lecture.LectureActivity;

public class LectureBoardListAdapter extends RecyclerView.Adapter<LectureBoardListAdapter.ViewHolder> {

    Context context;
    private List<Lecture> lectures;

    public LectureBoardListAdapter(Context context, List<Lecture> lectures) {
        this.context = context;
        this.lectures = lectures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lecture_list_item, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lectureName.setText(lectures.get(position).getName());

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String date = dateFormat.format(lectures.get(position).getCreatedDate());
        holder.lectureDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lecture_name)
        TextView lectureName;

        @BindView(R.id.lecture_date)
        TextView lectureDate;

        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
            itemView.setOnClickListener(this::showLecture);
        }

        void showLecture(View view) {
            Intent intent = new Intent(context, LectureActivity.class);
            context.startActivity(intent);
        }
    }
}
