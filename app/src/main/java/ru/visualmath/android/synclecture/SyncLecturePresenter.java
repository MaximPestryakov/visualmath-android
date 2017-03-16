package ru.visualmath.android.synclecture;

import android.os.Handler;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.visualmath.android.api.VisualMathSync;

@InjectViewState
public class SyncLecturePresenter extends MvpPresenter<SyncLectureView> {

    private VisualMathSync syncApi;

    public void connect(String ongoingId) {
        Handler handler = new Handler();
        syncApi = new VisualMathSync.Builder(ongoingId)
                .setOnConnectListener(__ -> Log.d("MyTag", "Connected"))
                .setOnDisconnectListener(__ -> Log.d("MyTag", "Disconnected"))
                .setOnFinishListener(__ -> Log.d("MyTag", "Finished"))
                .setOnModuleListener((slideInfo, module) -> {
                    handler.post(() -> {
                        getViewState().show("module");
                    });
                    Log.d("MyTag", "module");
                })
                .setOnQuestionListener((slideInfo, question) -> {
                    Log.d("MyTag", "question");
                })
                .setOnQuestionBlockListener((slideInfo, questionBlock) -> {
                    Log.d("MyTag", "questionBlock");
                })
                .build();
        syncApi.connect();
    }
}
