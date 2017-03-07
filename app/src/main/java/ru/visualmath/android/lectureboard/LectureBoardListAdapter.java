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
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.lecture.LectureActivity;

class LectureBoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
    private Context context;
    private List<SyncLecture> syncLectures;
    private List<Lecture> lectures;

    LectureBoardListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ViewType.SUBHEADER;
        }
        if (syncLectures != null && !syncLectures.isEmpty() && position == syncLectures.size() + 1) {
            return ViewType.SUBHEADER;
        }
        return ViewType.ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ViewType.SUBHEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_subheader, parent, false);
            return new ListSubheaderViewHolder(view);
        }

        if (viewType == ViewType.ITEM) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lecture_list_item, parent, false);
            return new LectureViewHolder(view, context);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (syncLectures != null && !syncLectures.isEmpty()) {
            if (position == 0) {
                ((ListSubheaderViewHolder) holder).listSubheader.setText(R.string.active_lecture);
            } else if (position == syncLectures.size() + 1) {
                ((ListSubheaderViewHolder) holder).listSubheader.setText(R.string.inactive_lecture);
            } else if (position > syncLectures.size()) {
                Lecture lecture = lectures.get(position - syncLectures.size() - 2);
                ((LectureViewHolder) holder).setLecture(lecture);
            } else {
                SyncLecture syncLecture = syncLectures.get(position - 1);
                ((LectureViewHolder) holder).setSyncLecture(syncLecture);
            }
        } else {
            if (position == 0) {
                ((ListSubheaderViewHolder) holder).listSubheader.setText(R.string.inactive_lecture);
            } else {
                Lecture lecture = lectures.get(position - 1);
                ((LectureViewHolder) holder).setLecture(lecture);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (syncLectures != null && !syncLectures.isEmpty()) {
            count += syncLectures.size() + 1;
        }
        if (lectures != null && !lectures.isEmpty()) {
            count += lectures.size() + 1;
        }
        return count;
    }

    void setLectures(List<SyncLecture> syncLectures, List<Lecture> lectures) {
        this.syncLectures = syncLectures;
        this.lectures = lectures;
        notifyDataSetChanged();
    }

    static class ViewType {
        private static final int SUBHEADER = 0;
        private static final int ITEM = 1;
    }

    static class ListSubheaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.listSubheader)
        TextView listSubheader;

        ListSubheaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LectureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lecture_name)
        TextView lectureName;

        @BindView(R.id.lecture_date)
        TextView lectureDate;

        Context context;

        SyncLecture syncLecture;
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


        void setSyncLecture(SyncLecture syncLecture) {
            this.syncLecture = syncLecture;
            lectureName.setText(syncLecture.name);
            String date = DATE_FORMAT.format(syncLecture.getCreatedDate());
            lectureDate.setText(date);
        }

        void setLecture(Lecture lecture) {
            this.lecture = lecture;
            lectureName.setText(lecture.getName());
            String date = DATE_FORMAT.format(lecture.getCreatedDate());
            lectureDate.setText(date);
        }
    }
}
