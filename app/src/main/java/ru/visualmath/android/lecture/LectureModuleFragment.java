package ru.visualmath.android.lecture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;

public class LectureModuleFragment extends Fragment {

    private static final String NAME_KEY = "name";
    private static final String CONTENT_KEY = "content";
    private static final String IMAGES_KEY = "images";
    @BindView(R.id.lecture_module_name)
    TextView nameTextView;
    @BindView(R.id.lecture_module_content)
    TextView contentTextView;
    private Unbinder unbinder;
    private String name;
    private String content;
    private ArrayList<String> images;

    public static LectureModuleFragment newInstance(String name, String content,
                                                    List<String> images) {
        Bundle args = new Bundle();
        args.putString(NAME_KEY, name);
        args.putString(CONTENT_KEY, content);
        args.putStringArrayList(IMAGES_KEY, (ArrayList<String>) images);

        LectureModuleFragment fragment = new LectureModuleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString(NAME_KEY);
            content = args.getString(CONTENT_KEY);
            images = args.getStringArrayList(IMAGES_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_module, container, false);
        unbinder = ButterKnife.bind(this, view);

        nameTextView.setText(name);
        contentTextView.setText(content);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}