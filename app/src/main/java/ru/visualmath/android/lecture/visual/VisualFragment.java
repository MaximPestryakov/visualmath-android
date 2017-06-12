package ru.visualmath.android.lecture.visual;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.graphics.Bitmap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Visual;

public class VisualFragment extends Fragment {
    public static final String TAG = "VisualFragment";

    private static final String ARGUMENT_VISUAL = "ARGUMENT_VISUAL";

    @BindView(R.id.header)
    TextView header;

    @BindView(R.id.lecture_visual)
    WebView webView;

    private Unbinder unbinder;
    private Visual visual;

    public static VisualFragment newInstance(Visual visual) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_VISUAL, visual);

        VisualFragment fragment = new VisualFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            visual = (Visual) args.getSerializable(ARGUMENT_VISUAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_visual, container, false);
        unbinder = ButterKnife.bind(this, view);

        header.setText(visual.getName());

        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBlockNetworkLoads(false);
        String url = "http://www.sync.visualmath.ru/visuals/" + visual.getId() + "/";
        webView.loadUrl(url);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }
    }
}
