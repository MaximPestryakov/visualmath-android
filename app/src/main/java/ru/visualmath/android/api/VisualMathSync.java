package ru.visualmath.android.api;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.socket.client.IO;
import io.socket.client.Socket;
import ru.visualmath.android.App;
import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.api.model.QuestionBlockSlide;
import ru.visualmath.android.api.model.SlideInfo;

public class VisualMathSync {

    private static final String URI = "http://sync.visualmath.ru";
    private static final String PATH = "/ws";

    private Socket socket;
    private String ongoingId;

    private Consumer<Module> onModuleListener;
    private BiConsumer<Question, Boolean> onQuestionListener;
    private Consumer<QuestionBlockSlide> onQuestionBlockListener;

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
            SlideInfo slideInfo = App.getGson().fromJson(jsonString, SlideInfo.class);
            String contentJson = null;
            try {
                contentJson = jsonObject.getJSONObject("content").toString();
            } catch (JSONException ignored) {
            }

            switch (slideInfo.getType()) {
                case MODULE:
                    if (onModuleListener != null) {
                        Module module = new Gson().fromJson(contentJson, Module.class);
                        mainThreadHandler.post(() -> {
                            try {
                                onModuleListener.accept(module);
                            } catch (Exception ignored) {
                            }
                        });
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
                        mainThreadHandler.post(() -> {
                            try {
                                onQuestionListener.accept(question, finalIsStarted);
                            } catch (Exception ignored) {
                            }
                        });
                    }
                    return;
                case QUESTION_BLOCK:
                    if (onQuestionBlockListener != null) {
                        QuestionBlockSlide slide = App.getGson().fromJson(jsonString, QuestionBlockSlide.class);
                        mainThreadHandler.post(() -> {
                            try {
                                onQuestionBlockListener.accept(slide);
                            } catch (Exception ignored) {
                            }
                        });
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

        public Builder setOnConnectListener(Runnable onConnectListener) {
            api.socket.on(Socket.EVENT_CONNECT, __ -> api.mainThreadHandler.post(onConnectListener));
            return this;
        }

        public Builder setOnDisconnectListener(Runnable onDisconnectListener) {
            api.socket.on(Socket.EVENT_DISCONNECT, __ -> api.mainThreadHandler.post(onDisconnectListener));
            return this;
        }

        public Builder setOnModuleListener(Consumer<Module> onModuleListener) {
            api.onModuleListener = onModuleListener;
            return this;
        }

        public Builder setOnQuestionListener(BiConsumer<Question, Boolean> onQuestionListener) {
            api.onQuestionListener = onQuestionListener;
            return this;
        }

        public Builder setOnQuestionBlockListener(Consumer<QuestionBlockSlide> onQuestionBlockListener) {
            api.onQuestionBlockListener = onQuestionBlockListener;
            return this;
        }

        public Builder setOnFinishListener(Runnable onFinishListener) {
            api.socket.on("sync_v1/lectures/finish:" + api.ongoingId, __ -> {
                api.mainThreadHandler.post(onFinishListener);
                api.socket.disconnect();
            });
            return this;
        }

        public Builder setOnStartQuestion(String questionId, Runnable onStartQuestion) {
            api.socket.on("sync_v1/questions:" + api.ongoingId + ":" + questionId, json -> {
                JSONObject jsonObject = (JSONObject) json[0];
                String type = "";
                try {
                    type = jsonObject.getString("type");
                } catch (JSONException ignored) {
                }
                if ("QUESTION_START".equals(type)) {
                    api.mainThreadHandler.post(onStartQuestion);
                }
            });
            return this;
        }

        public Builder setOnFinishQuestion(String questionId, Runnable onFinishQuestion) {
            api.socket.on("sync_v1/questions:" + api.ongoingId + ":" + questionId, json -> {
                JSONObject jsonObject = (JSONObject) json[0];
                String type = "";
                try {
                    type = jsonObject.getString("type");
                } catch (JSONException ignored) {
                }
                if ("QUESTION_FINISH".equals(type)) {
                    api.mainThreadHandler.post(onFinishQuestion);
                }
            });
            return this;
        }

        public Builder setOnStartBlock(String blockId, Runnable onStartBlock) {
            api.socket.on("sync_v1/blocks:" + api.ongoingId + ":" + blockId, json -> {
                JSONObject jsonObject = (JSONObject) json[0];
                String type = "";
                try {
                    type = jsonObject.getString("type");
                } catch (JSONException ignored) {
                }
                if ("BLOCK_START".equals(type)) {
                    api.mainThreadHandler.post(onStartBlock);
                }
            });
            return this;
        }

        public Builder setOnFinishBlock(String blockId, Runnable onFinishBlock) {
            api.socket.on("sync_v1/blocks:" + api.ongoingId + ":" + blockId, json -> {
                JSONObject jsonObject = (JSONObject) json[0];
                String type = "";
                try {
                    type = jsonObject.getString("type");
                } catch (JSONException ignored) {
                }
                if ("BLOCK_FINISH".equals(type)) {
                    api.mainThreadHandler.post(onFinishBlock);
                }
            });
            return this;
        }

        public VisualMathSync build() {
            return api;
        }
    }
}
