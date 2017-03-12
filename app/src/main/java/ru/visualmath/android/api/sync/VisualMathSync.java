package ru.visualmath.android.api.sync;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class VisualMathSync {
    private static String LECTURE_START = "LECTURE_START";
    private static String LECTURE_FINISH = "LECTURE_FINISH";
    private static String SLIDE = "slide";
    private static String QUESTION = "question";
    private static String QUESTION_START = "QUESTION_START";
    private static String QUESTION_FINISH = "QUESTION_FINISH";
    private static String QUESTION_BLOCK = "questionBlock";

    private URI address;
    private Socket socket;
    private String state;
    private String ongoingId;
    private int index;

    public String getOngoingId() {
        return this.ongoingId;
    }
    public String getState() {
        return this.state;
    }
    public int getIndex() {return this.index;}

    private Callback<Slide> onSimpleSlide;
    private Callback<Question> onQuestion;
    private Callback<QuestionBlock> onQuestionBlock;
    private Callback<String> onLectureStart;;
    private Callback<String> onLectureFinish;

    public VisualMathSync setSlideCallback(Callback<Slide> nextSlideCallback) {
        this.onSimpleSlide = nextSlideCallback;
        return this;
    }

    public VisualMathSync setQuestionCallback(Callback <Question> nextQuestionCallback) {
        this.onQuestion = nextQuestionCallback;
        return this;
    }

    public VisualMathSync setQuestionBlockCallback(Callback <QuestionBlock> nextQuestionBlockCallback) {
        this.onQuestionBlock = nextQuestionBlockCallback;
        return this;
    }

    public VisualMathSync setLectureFinishCallback(Callback<String> lectureFinishCallback) {
        this.onLectureFinish = lectureFinishCallback;
        return this;
    }

    public VisualMathSync setLectureStartCallback(Callback<String> lectureStartCallback) {
        this.onLectureStart = lectureStartCallback;
        return this;
    }

    public static void main(String[] args) {
        try {
            VisualMathSync api = new VisualMathSync(new URI("http://sync.visualmath.ru"));
            api.setLectureStartCallback(state -> {
                System.out.println(state);
            }).setLectureFinishCallback(state -> {
                System.out.println(state);
            }).setSlideCallback(slide -> {
                System.out.println(slide);
            }).setQuestionCallback(question -> {
                System.out.println(question);
            }).setQuestionBlockCallback(questionBlock -> {
                System.out.println(questionBlock);
            });

            api.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public VisualMathSync(URI address) throws URISyntaxException {
        this.address = address;
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.path = "/ws";

        socket = IO.socket(address, opts);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("Connected to " + address);
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("Disconnected from " + address);
            }
        }).on("sync_v1/lectures", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONObject json = ((JSONObject)objects[0]);
                try {
                    ongoingId = parseOngoingId(json);
                    state = parseType(json);
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }

                if (state.equals(LECTURE_START)) {
                    onLectureStart.accept(LECTURE_START);
                    addSlideSocketListener();
                } else if (state.equals(LECTURE_FINISH)) {
                    onLectureFinish.accept(LECTURE_FINISH);
                }
            }
        });
    }

    public void connect() {
        socket.connect();
    }
    public void disconnect() {socket.disconnect();}
    public void reconnect() {socket.io().reconnection();}

    private void addSlideSocketListener() {
        socket.on("sync_v1/ongoing_lectures/set_slide:" + getOngoingId() , objects -> {
            try {
                parseSlide((JSONObject) objects[0]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        socket.io().reconnection();
    }

    private void addQuestionSocketListener(String questionId) {
        socket.on("sync_v1/questions:" + getOngoingId() + ":" + questionId, objects -> {
            try {
                String action = (String)((JSONObject)objects[0]).get("type");
                System.out.println(action);
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            }
        });

        socket.io().reconnection();
    }

    private void addQuestionBlockSocketListener(String questionBlockId) {
        socket.on("sync_v1/blocks:" + getOngoingId() + ":" + questionBlockId, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                try {
                    String action = (String)((JSONObject)objects[0]).get("type");
                    System.out.println(action);
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private void parseSlide(JSONObject obj) throws JSONException, Exception {
        String tempState = (String) obj.get("type");
        this.index = (int)obj.get("index");

        if (tempState.equals(SLIDE)) {
            state = tempState;
            JSONObject textSlide = (JSONObject)obj.get("content");
            parseTextSlideBody(textSlide);
        } else if (tempState.equals(QUESTION)) {
            state = tempState;
            JSONObject questionSlide = (JSONObject)obj.get("content");
            Question question = parseQuestionSlideBody(questionSlide);
            onQuestion.accept(question);
            addQuestionSocketListener(question.getId());
        } else if (tempState.equals(QUESTION_BLOCK)) {
            JSONObject questionBlockSlide = (JSONObject)obj.get("content");
            QuestionBlock questionBlock = parseQuestionBlockSlideBody(questionBlockSlide);
            onQuestionBlock.accept(questionBlock);
            addQuestionBlockSocketListener(questionBlock.getId());
        } else {
            System.out.println("wrong signal implemented on set_slide");
        }
    }

    private void parseTextSlideBody(JSONObject obj) throws Exception {
        String content = (String)obj.get("content");
        String name = (String)obj.get("name");

        onSimpleSlide.accept(new Slide(name, content));
    }

    private QuestionBlock parseQuestionBlockSlideBody(JSONObject obj) throws Exception {
        String id = (String)obj.get("_id");
        String name = (String)obj.get("name");
        QuestionBlock questionBlock = new QuestionBlock(name, id);

        JSONArray jsonQuestionsIds = (JSONArray)obj.get("questionsIds");

        for (int i = 0; i < jsonQuestionsIds.length(); ++i) {
            questionBlock.addQuestion(parseQuestionSlideBody(jsonQuestionsIds.getJSONObject(i)));
        }
        return questionBlock;
    }

    private Question parseQuestionSlideBody(JSONObject obj) throws Exception {
        String question = (String)obj.get("question");
        JSONArray jsonAnswers = (JSONArray)obj.get("answers");
        JSONArray jsonCorrectAnswers = (JSONArray)obj.get("correctAnswers");
        String id = (String)obj.get("_id");

        ArrayList<String> answersList = new ArrayList<>();
        for (int i = 0; i < jsonAnswers.length(); ++i) {
            answersList.add(jsonAnswers.getString(i));
        }

        ArrayList<String> correctAnswersList = new ArrayList<>();
        for (int i = 0; i < jsonCorrectAnswers.length(); ++i) {
            correctAnswersList.add(jsonCorrectAnswers.getString(i));
        }

        return new Question(question, answersList, correctAnswersList, id);
    }

    private JSONObject parseShortLecture(JSONObject obj) throws JSONException{
        return (JSONObject)obj.get("shortLecture");
    }

    private String parseOngoingId(JSONObject obj) throws JSONException{
        JSONObject shortLectureObj = parseShortLecture(obj);
        return (String)shortLectureObj.get("ongoingId");
    }

    private String parseType(JSONObject obj) throws JSONException{
        return (String)obj.get("type");
    }
}