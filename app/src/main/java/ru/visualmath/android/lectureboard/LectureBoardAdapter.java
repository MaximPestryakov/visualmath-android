package ru.visualmath.android.lectureboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.lecture.LectureActivity;
import ru.visualmath.android.synclecture.SyncLectureActivity;
import ru.visualmath.android.util.BindableViewHolder;

class LectureBoardAdapter extends RecyclerView.Adapter<BindableViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
    private Context context;
    private List<SyncLecture> syncLectures = Collections.emptyList();
    private List<Lecture> lectures = Collections.emptyList();

    LectureBoardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || (!syncLectures.isEmpty() && position == syncLectures.size() + 1)) {
            return HEADER;
        }
        return ITEM;
    }

    @Override
    public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lecture_list_item, parent, false);
        return new LectureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BindableViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (!syncLectures.isEmpty()) {
            count += syncLectures.size() + 1;
        }
        if (!lectures.isEmpty()) {
            count += lectures.size() + 1;
        }
        return count;
    }

    void setLectures(List<SyncLecture> syncLectures, List<Lecture> lectures) {
        this.syncLectures = syncLectures;
        this.lectures = lectures;
        notifyDataSetChanged();
    }

    class HeaderViewHolder extends BindableViewHolder {

        @BindView(R.id.header)
        TextView header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position) {
            if (!syncLectures.isEmpty() && position == 0) {
                header.setText(R.string.active_lecture);
            } else {
                header.setText(R.string.inactive_lecture);
            }
        }
    }

    class LectureViewHolder extends BindableViewHolder {

        @BindView(R.id.lecture_name)
        TextView lectureName;

        @BindView(R.id.lecture_date)
        TextView lectureDate;

        LectureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position) {
            if (syncLectures.isEmpty()) {
                setLecture(lectures.get(position - 1));
            } else if (position > syncLectures.size()) {
                setLecture(lectures.get(position - syncLectures.size() - 2));
            } else {
                setSyncLecture(syncLectures.get(position - 1));
            }
        }

        void setSyncLecture(SyncLecture syncLecture) {
            lectureName.setText(syncLecture.name);
            String date = DATE_FORMAT.format(syncLecture.getCreatedDate());
            lectureDate.setText(date);
            itemView.setOnClickListener(v -> showSyncLecture(syncLecture.ongoingId));
        }

        void setLecture(Lecture lecture) {
            lectureName.setText(lecture.getName());
            String date = DATE_FORMAT.format(lecture.getCreatedDate());
            lectureDate.setText(date);
            itemView.setOnClickListener(v -> showLecture(lecture));
        }

        void showSyncLecture(String lectureId) {
            context.startActivity(SyncLectureActivity.getStartIntent(context, lectureId));
        }

        void showLecture(Lecture lecture) {
            context.startActivity(LectureActivity.getStartIntent(context, lecture));
        }
    }
}
