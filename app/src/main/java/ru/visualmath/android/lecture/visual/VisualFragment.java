package ru.visualmath.android.lecture.visual;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Visual;

public class VisualFragment extends Fragment {
    public static final String TAG = "VisualFragment";

    private static final String ARGGUMENT_VISUAL = "ARGUMENT_VISUAL";

    @BindView(R.id.header)
    TextView header;

    @BindView(R.id.lecture_visual)
    WebView webView;

    private Unbinder unbinder;
    private Visual visual;

    public static VisualFragment newInstance(Visual visual) {
        Bundle args = new Bundle();
        args.putSerializable(ARGGUMENT_VISUAL, visual);

        VisualFragment fragment = new VisualFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            visual = (Visual) args.getSerializable(ARGGUMENT_VISUAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_visual, container, false);
        unbinder = ButterKnife.bind(this, view);

        header.setText(visual.getName());
        webView.loadUrl(visual.getUrl());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
