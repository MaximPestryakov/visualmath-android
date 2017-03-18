package ru.visualmath.android.synclecture;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;

public class SyncLectureActivity extends MvpAppCompatActivity implements SyncLectureView {

    @InjectPresenter
    SyncLecturePresenter presenter;

    @BindView(R.id.lectureName)
    WebView lectureName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_lecture);
        ButterKnife.bind(this);

        String ongoingId = getIntent().getStringExtra("ongoing_id");

        lectureName.getSettings().setJavaScriptEnabled(true);
        lectureName.loadUrl("file:///android_asset/index.html");

        presenter.connect(ongoingId);
    }

    @Override
    public void showModule(String name, String content) {
        lectureName.addJavascriptInterface(new ObjectToJs(name, content), "slide");
        lectureName.reload();
    }

    private static class ObjectToJs {
        private String name;
        private String content;

        ObjectToJs(String name, String content) {
            this.name = name;
            this.content = content;
        }

        @JavascriptInterface
        String getName() {
            return name;
        }

        @JavascriptInterface
        String getContent() {
            return content;
        }
    }
}
