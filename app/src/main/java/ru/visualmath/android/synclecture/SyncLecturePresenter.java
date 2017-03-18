package ru.visualmath.android.synclecture;

import android.os.Handler;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.VisualMathSync;
import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.SlideInfo;

@InjectViewState
public class SyncLecturePresenter extends MvpPresenter<SyncLectureView> {

    private VisualMathApi api;

    public SyncLecturePresenter() {
        this.api = VisualMathApi.getApi();
    }

    void connect(String ongoingId) {
        api.loadSyncSlide(ongoingId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    String jsonString = responseBody.string();
                    SlideInfo slideInfo = new Gson().fromJson(jsonString, SlideInfo.class);
                    String contentJson = null;
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        contentJson = jsonObject.getJSONObject("content").toString();
                    } catch (JSONException ignored) {
                    }

                    switch (slideInfo.getType()) {
                        case MODULE:
                            Module module = new Gson().fromJson(contentJson, Module.class);
                            getViewState().showModule(module.getName(), module.getContent());
                            return;
                        case QUESTION:
                            Question question = new Gson().fromJson(contentJson, Question.class);
                            getViewState().showModule(question.getQuestion(), "");
                            return;
                        case QUESTION_BLOCK:
                            QuestionBlock questionBlock = new Gson().fromJson(contentJson, QuestionBlock.class);
                            getViewState().showModule(questionBlock.getName(), "");
                            break;
                    }
                });

        Handler handler = new Handler();
        VisualMathSync syncApi = new VisualMathSync.Builder(ongoingId)
                .setOnConnectListener(__ -> Log.d("MyTag", "Connected"))
                .setOnDisconnectListener(__ -> Log.d("MyTag", "Disconnected"))
                .setOnFinishListener(__ -> Log.d("MyTag", "Finished"))
                .setOnModuleListener((slideInfo, module) -> {
                    handler.post(() -> {
                        getViewState().showModule(module.getName(), module.getContent());
                    });
                    Log.d("MyTag", "module");
                })
                .setOnQuestionListener((slideInfo, question) -> {
                    handler.post(() -> {
                        getViewState().showModule(question.getQuestion(), "");
                    });
                    Log.d("MyTag", "question");
                })
                .setOnQuestionBlockListener((slideInfo, questionBlock) -> {
                    handler.post(() -> {
                        getViewState().showModule(questionBlock.getName(), "");
                    });
                    Log.d("MyTag", "questionBlock");
                })
                .build();
        syncApi.connect();
    }
}
