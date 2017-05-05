package ru.visualmath.android.api;

import android.os.Handler;
import android.os.Looper;

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
    private String ongoingId;

    private BiConsumer<SlideInfo, Module> onModuleListener;
    private BiConsumer<Question, Boolean> onQuestionListener;
    private BiConsumer<SlideInfo, QuestionBlock> onQuestionBlockListener;

    private Handler mainThreadHandler;

    private VisualMathSync(String ongoingId) {
        this.ongoingId = ongoingId;

        mainThreadHandler = new Handler(Looper.getMainLooper());

        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.path = PATH;

        try {
            socket = IO.socket(URI, options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on("sync_v1/ongoing_lectures/set_slide:" + ongoingId, json -> {
            if (onModuleListener == null && onQuestionListener == null && onQuestionBlockListener == null) {
                return;
            }
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
                    if (onModuleListener != null) {
                        Module module = new Gson().fromJson(contentJson, Module.class);
                        mainThreadHandler.post(() -> onModuleListener.accept(slideInfo, module));
                    }
                    return;
                case QUESTION:
                    if (onQuestionListener != null) {
                        Question question = new Gson().fromJson(contentJson, Question.class);
                        boolean isStarted = !jsonObject.isNull("activeContent");
                        if (isStarted) {
                            try {
                                isStarted = !jsonObject.getJSONObject("activeContent").getBoolean("ended");
                            } catch (JSONException ignored) {
                            }
                        }
                        boolean finalIsStarted = isStarted;
                        mainThreadHandler.post(() -> onQuestionListener.accept(question, finalIsStarted));
                    }
                    return;
                case QUESTION_BLOCK:
                    if (onQuestionBlockListener != null) {
                        QuestionBlock questionBlock = new Gson().fromJson(contentJson, QuestionBlock.class);
                        mainThreadHandler.post(() -> onQuestionBlockListener.accept(slideInfo, questionBlock));
                    }
                    break;
            }
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

        public Builder setOnConnectListener(Consumer onConnectListener) {
            api.socket.on(Socket.EVENT_CONNECT, __ -> api.mainThreadHandler.post(onConnectListener::accept));
            return this;
        }

        public Builder setOnDisconnectListener(Consumer onDisconnectListener) {
            api.socket.on(Socket.EVENT_DISCONNECT, __ -> api.mainThreadHandler.post(onDisconnectListener::accept));
            return this;
        }

        public Builder setOnModuleListener(BiConsumer<SlideInfo, Module> onModuleListener) {
            api.onModuleListener = onModuleListener;
            return this;
        }

        public Builder setOnQuestionListener(BiConsumer<Question, Boolean> onQuestionListener) {
            api.onQuestionListener = onQuestionListener;
            return this;
        }

        public Builder setOnQuestionBlockListener(BiConsumer<SlideInfo, QuestionBlock> onQuestionBlockListener) {
            api.onQuestionBlockListener = onQuestionBlockListener;
            return this;
        }

        public Builder setOnFinishListener(Consumer onFinishListener) {
            api.socket.on("sync_v1/lectures/finish:" + api.ongoingId, __ -> {
                api.mainThreadHandler.post(onFinishListener::accept);
                api.socket.disconnect();
            });
            return this;
        }

        public Builder setOnStartQuestion(String questionId, Consumer onStartQuestion) {
            api.socket.on("sync_v1/questions:" + api.ongoingId + ":" + questionId, json -> {
                JSONObject jsonObject = (JSONObject) json[0];
                String type = "";
                try {
                    type = jsonObject.getString("type");
                } catch (JSONException ignored) {
                }
                if ("QUESTION_START".equals(type)) {
                    api.mainThreadHandler.post(onStartQuestion::accept);
                }
            });
            return this;
        }

        public Builder setOnFinishQuestion(String questionId, Consumer onFinishQuestion) {
            api.socket.on("sync_v1/questions:" + api.ongoingId + ":" + questionId, json -> {
                JSONObject jsonObject = (JSONObject) json[0];
                String type = "";
                try {
                    type = jsonObject.getString("type");
                } catch (JSONException ignored) {
                }
                if ("QUESTION_FINISH".equals(type)) {
                    api.mainThreadHandler.post(onFinishQuestion::accept);
                }
            });
            return this;
        }

        public VisualMathSync build() {
            return api;
        }
    }
}
