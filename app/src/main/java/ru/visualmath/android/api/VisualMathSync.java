package ru.visualmath.android.api;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.SlideInfo;
import ru.visualmath.android.util.BiConsumer;
import ru.visualmath.android.util.Consumer;

public class VisualMathSync {

    private static final String URI = "http://sync.visualmath.ru";
    private static final String PATH = "/ws";

    private Socket socket;

    private Consumer<Void> onConnectListener;
    private Consumer<Void> onDisconnectListener;

    private BiConsumer<SlideInfo, Module> onModuleListener;
    private BiConsumer<SlideInfo, Question> onQuestionListener;
    private BiConsumer<SlideInfo, QuestionBlock> onQuestionBlockListener;

    private Consumer<Void> onFinishListener;

    private VisualMathSync(String ongoingId) {

        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.path = PATH;

        try {
            socket = IO.socket(URI, options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, __ -> onConnectListener.accept(null)
        ).on(Socket.EVENT_DISCONNECT, __ -> onDisconnectListener.accept(null)
        ).on("sync_v1/ongoing_lectures/set_slide:" + ongoingId, json -> {
            JSONObject jsonObject = (JSONObject) json[0];
            String jsonString = jsonObject.toString();
            SlideInfo slideInfo = new Gson().fromJson(jsonString, SlideInfo.class);
            String contentJson = null;
            try {
                contentJson = jsonObject.getJSONObject("content").toString();
            } catch (JSONException ignored) {
            }

            switch (slideInfo.getType()) {
                case MODULE:
                    Module module = new Gson().fromJson(contentJson, Module.class);
                    onModuleListener.accept(slideInfo, module);
                    return;
                case QUESTION:
                    Question question = new Gson().fromJson(contentJson, Question.class);
                    onQuestionListener.accept(slideInfo, question);
                    return;
                case QUESTION_BLOCK:
                    QuestionBlock questionBlock = new Gson().fromJson(contentJson, QuestionBlock.class);
                    onQuestionBlockListener.accept(slideInfo, questionBlock);
                    break;
            }
        }).on("sync_v1/lectures/finish:" + ongoingId, __ -> {
            onFinishListener.accept(null);
            socket.disconnect();
        });
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public static class Builder {

        private VisualMathSync api;

        public Builder(String ongoingId) {
            api = new VisualMathSync(ongoingId);
        }

        public Builder setOnConnectListener(Consumer<Void> onConnectListener) {
            api.onConnectListener = onConnectListener;
            return this;
        }

        public Builder setOnDisconnectListener(Consumer<Void> onDisconnectListener) {
            api.onDisconnectListener = onDisconnectListener;
            return this;
        }

        public Builder setOnModuleListener(BiConsumer<SlideInfo, Module> onModuleListener) {
            api.onModuleListener = onModuleListener;
            return this;
        }

        public Builder setOnQuestionListener(BiConsumer<SlideInfo, Question> onQuestionListener) {
            api.onQuestionListener = onQuestionListener;
            return this;
        }

        public Builder setOnQuestionBlockListener(BiConsumer<SlideInfo, QuestionBlock> onQuestionBlockListener) {
            api.onQuestionBlockListener = onQuestionBlockListener;
            return this;
        }

        public Builder setOnFinishListener(Consumer<Void> onFinishListener) {
            api.onFinishListener = onFinishListener;
            return this;
        }

        public VisualMathSync build() {
            return api;
        }
    }
}
