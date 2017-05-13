package ru.visualmath.android.synclecture;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.visualmath.android.App;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.VisualMathSync;
import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlockSlide;
import ru.visualmath.android.api.model.SlideInfo;

@InjectViewState
public class SyncLecturePresenter extends MvpPresenter<SyncLectureView> {

    private final VisualMathApi api = VisualMathApi.getApi();

    private VisualMathSync syncApi;

    void connect(String lectureId) {
        api.loadSyncLecture(lectureId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> {
                    String jsonString = responseBody.string();
                    JSONObject jsonObject = new JSONObject(jsonString);
                    return jsonObject.getBoolean("ended");
                }).flatMap(isEnded -> {
            if (isEnded) {
                getViewState().showFinish();
                return Observable.empty();
            }
            return api.loadSyncSlide(lectureId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }).subscribe(responseBody -> {
            String jsonString = responseBody.string();
            if (jsonString.isEmpty()) {
                return;
            }
            SlideInfo slideInfo = new Gson().fromJson(jsonString, SlideInfo.class);

            JSONObject jsonObject = new JSONObject(jsonString);
            String contentJson = jsonObject.getJSONObject("content").toString();

            boolean isStarted;
            switch (slideInfo.getType()) {
                case MODULE:
                    Module module = new Gson().fromJson(contentJson, Module.class);
                    getViewState().showModule(module);
                    break;

                case QUESTION:
                    Question question = new Gson().fromJson(contentJson, Question.class);
                    isStarted = !jsonObject.isNull("activeContent");
                    if (isStarted) {
                        isStarted = !jsonObject.getJSONObject("activeContent").getBoolean("ended");
                    }
                    getViewState().showQuestion(question, isStarted);
                    break;

                case QUESTION_BLOCK:
                    QuestionBlockSlide slide = App.getGson().fromJson(jsonString, QuestionBlockSlide.class);
                    getViewState().showQuestionBlock(slide);
                    break;
            }

            syncApi = new VisualMathSync.Builder(lectureId)
                    .setOnConnectListener(() -> Log.d("MyTag", "Connected"))
                    .setOnDisconnectListener(() -> Log.d("MyTag", "Disconnected"))
                    .setOnFinishListener(getViewState()::showFinish)
                    .setOnModuleListener(getViewState()::showModule)
                    .setOnQuestionListener(getViewState()::showQuestion)
                    .setOnQuestionBlockListener(getViewState()::showQuestionBlock)
                    .build();
            syncApi.connect();

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (syncApi != null) {
            syncApi.disconnect();
        }
    }
}
