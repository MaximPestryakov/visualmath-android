package ru.visualmath.android.lectureboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.lecture.LectureActivity;

class LectureBoardListAdapter extends RecyclerView.Adapter<LectureBoardListAdapter.LectureViewHolder> {

    private Context context;
    private List<Lecture> lectures;
    private DateFormat dateFormat;

    LectureBoardListAdapter(Context context) {
        this.context = context;
        this.dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
    }

    @Override
    public LectureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lecture_list_item, parent, false);
        return new LectureViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(LectureViewHolder holder, int position) {
        Lecture lecture = lectures.get(position);

        holder.setLecture(lecture);
        holder.lectureName.setText(lecture.getName());

        String date = dateFormat.format(lecture.getCreatedDate());
        holder.lectureDate.setText(date);
    }

    @Override
    public int getItemCount() {
        if (lectures == null) {
            return 0;
        }
        return lectures.size();
    }

    void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
        notifyDataSetChanged();
    }

    static class LectureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lecture_name)
        TextView lectureName;

        @BindView(R.id.lecture_date)
        TextView lectureDate;

        Context context;

        Lecture lecture;

        LectureViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
            itemView.setOnClickListener(v -> showLecture());
        }

        void showLecture() {
            Intent intent = new Intent(context, LectureActivity.class);
            intent.putExtra("lecture", new Gson().toJson(lecture));
            context.startActivity(intent);
        }

        void setLecture(Lecture lecture) {
            this.lecture = lecture;
        }
    }
}
