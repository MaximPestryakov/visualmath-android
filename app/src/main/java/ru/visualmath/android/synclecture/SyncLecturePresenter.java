package ru.visualmath.android.synclecture;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;

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

    private final VisualMathApi api = VisualMathApi.getApi();

    private VisualMathSync syncApi;

    void connect(String lectureId) {
        api.loadSyncSlide(lectureId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    String jsonString = responseBody.string();
                    SlideInfo slideInfo = new Gson().fromJson(jsonString, SlideInfo.class);

                    JSONObject jsonObject = new JSONObject(jsonString);
                    String contentJson = jsonObject.getJSONObject("content").toString();

                    switch (slideInfo.getType()) {
                        case MODULE:
                            Module module = new Gson().fromJson(contentJson, Module.class);
                            getViewState().showModule(module.getName(), module.getContent());
                            return;
                        case QUESTION:
                            Question question = new Gson().fromJson(contentJson, Question.class);
                            boolean isStarted = !jsonObject.isNull("activeContent");
                            if (isStarted) {
                                isStarted = !jsonObject.getJSONObject("activeContent").getBoolean("ended");
                            }
                            getViewState().showQuestion(question, isStarted);
                            return;
                        case QUESTION_BLOCK:
                            QuestionBlock questionBlock = new Gson().fromJson(contentJson, QuestionBlock.class);
                            getViewState().showModule(questionBlock.getName(), "");
                            break;
                    }
                });

        syncApi = new VisualMathSync.Builder(lectureId)
                .setOnConnectListener(() -> Log.d("MyTag", "Connected"))
                .setOnDisconnectListener(() -> Log.d("MyTag", "Disconnected"))
                .setOnFinishListener(() -> Log.d("MyTag", "Finished"))
                .setOnModuleListener((slideInfo, module) -> {
                    getViewState().showModule(module.getName(), module.getContent());
                    Log.d("MyTag", "module");
                })
                .setOnQuestionListener((question, isStarted) -> {
                    getViewState().showQuestion(question, isStarted);
                    Log.d("MyTag", "question");
                })
                .setOnQuestionBlockListener((slideInfo, questionBlock) -> {
                    getViewState().showModule(questionBlock.getName(), "");
                    Log.d("MyTag", "questionBlock");
                })
                .build();
        syncApi.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        syncApi.disconnect();
    }
}
