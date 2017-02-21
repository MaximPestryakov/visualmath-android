package ru.visualmath.android.lecture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;

public class LectureQuestionBlockFragment extends Fragment {

    private Unbinder unbinder;

    private String id;

    private static String ID_KEY = "id";

    public static LectureQuestionBlockFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(ID_KEY, id);

        LectureQuestionBlockFragment fragment = new LectureQuestionBlockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            id = args.getString(ID_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_question_block, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
