package ru.visualmath.android.lecture.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;

public class ModuleFragment extends Fragment {

    public static final String TAG = "ModuleFragment";
    private static final String NAME_KEY = "NAME_KEY";
    private static final String CONTENT_KEY = "CONTENT_KEY";
    private static final String IMAGES_KEY = "IMAGES_KEY";
    @BindView(R.id.lecture_module_name)
    KatexView nameTextView;
    @BindView(R.id.lecture_module_content)
    KatexView contentTextView;
    private Unbinder unbinder;
    private String name;
    private String content;
    private ArrayList<String> images;

    public static ModuleFragment newInstance(String name, String content) {
        Bundle args = new Bundle();
        args.putString(NAME_KEY, name);
        args.putString(CONTENT_KEY, content);

        ModuleFragment fragment = new ModuleFragment();
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
        //content = content.replaceAll("\\$", "\\$\\$");
        // contentTex.setText("Синус \\(\\sin^2{x}\\)");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
