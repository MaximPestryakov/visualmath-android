package ru.visualmath.android.api.sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;

public class VisualMathSync {
    private static String URL = "http://sync.visualmath.ru";
    private static String LECTURE_START = "LECTURE_START";
    private static String LECTURE_FINISH = "LECTURE_FINISH";
    private static String SLIDE = "slide";
    private static String QUESTION = "question";
    private static String QUESTION_START = "QUESTION_START";
    private static String QUESTION_FINISH = "QUESTION_FINISH";
    private static String QUESTION_BLOCK = "questionBlock";
    private static String QUESTION_BLOCK_START = "BLOCK_START";
    private static String QUESTION_BLOCK_FINISH = "BLOCK_FINISH";

    private Socket socket;
    private Lecture lecture;
    private String ongoingId;
    private int index;
    private Callback<Slide> onSimpleSlide;
    private Callback<Question> onQuestion;
    private Callback<QuestionBlock> onQuestionBlock;
    private Callback<Lecture> onLectureStart;
    private Callback<Lecture> onLectureFinish;
    private Callback<String> onQuestionStart;
    private Callback<String> onQuestionFinish;
    private Callback<String> onQuestionBlockStart;
    private Callback<String> onQuestionBlockFinish;

    public VisualMathSync(String ongoingId) {
        URI address = null;
        try {
            new URI(URL);
        } catch (URISyntaxException ignored) {
        }

        this.ongoingId = ongoingId;
        this.lecture = new Lecture();

        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.path = "/ws";

        socket = IO.socket(address, opts);

        socket.on(Socket.EVENT_CONNECT, args -> System.out.println("Connected to " + address))
                .on(Socket.EVENT_DISCONNECT, objects -> System.out.println("Disconnected from " + address))
                .on("sync_v1/lectures", objects -> {
                    JSONObject json = ((JSONObject) objects[0]);
                    try {
                        lecture.setState(parseType(json));
                        // this.ongoingId = parseOngoingId(json);
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                    }


                    if (getLecture().getState().equals(LECTURE_START)) {
                        onLectureStart.accept(getLecture());
                        addSlideSocketListener();
                    } else if (getLecture().getState().equals(LECTURE_FINISH)) {
                        onLectureFinish.accept(getLecture());
                    }
                });
    }

    public static void main(String[] args) {
        try {
            VisualMathSync api = new VisualMathSync("");
            api.setLectureStartCallback(lecture -> {
                System.out.println(lecture);
            }).setLectureFinishCallback(lecture -> {
                System.out.println(lecture);
            }).setSlideCallback(slide -> {
                System.out.println(slide);
            }).setQuestionCallback(question -> {
                System.out.println(question);
            }).setQuestionBlockCallback(questionBlock -> {
                System.out.println(questionBlock);
            }).setQuestionStartCallback(state -> {
                System.out.println(state);
            }).setQuestionFinishCallback(state -> {
                System.out.println(state);
            }).setQuestionBlockStartCallback(state -> {
                System.out.println(state);
            }).setQuestionBlockFinishCallback(state -> {
                System.out.println(state);
            });

            api.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getOngoingId() {
        return this.ongoingId;
    }

    public int getSlideIndex() {
        return this.index;
    }

    public Lecture getLecture() {
        return this.lecture;
    }

    public VisualMathSync setSlideCallback(Callback<Slide> nextSlideCallback) {
        this.onSimpleSlide = nextSlideCallback;
        return this;
    }

    public VisualMathSync setQuestionCallback(Callback<Question> nextQuestionCallback) {
        this.onQuestion = nextQuestionCallback;
        return this;
    }

    public VisualMathSync setQuestionBlockCallback(Callback<QuestionBlock> nextQuestionBlockCallback) {
        this.onQuestionBlock = nextQuestionBlockCallback;
        return this;
    }

    public VisualMathSync setLectureFinishCallback(Callback<Lecture> lectureFinishCallback) {
        this.onLectureFinish = lectureFinishCallback;
        return this;
    }

    public VisualMathSync setLectureStartCallback(Callback<Lecture> lectureStartCallback) {
        this.onLectureStart = lectureStartCallback;
        return this;
    }

    public VisualMathSync setQuestionStartCallback(Callback<String> questionStartCallback) {
        this.onQuestionStart = questionStartCallback;
        return this;
    }

    public VisualMathSync setQuestionFinishCallback(Callback<String> questionFinishCallback) {
        this.onQuestionFinish = questionFinishCallback;
        return this;
    }

    public VisualMathSync setQuestionBlockStartCallback(Callback<String> questionBlockStartCallback) {
        this.onQuestionBlockStart = questionBlockStartCallback;
        return this;
    }

    public VisualMathSync setQuestionBlockFinishCallback(Callback<String> questionBlockFinishCallback) {
        this.onQuestionBlockFinish = questionBlockFinishCallback;
        return this;
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public void reconnect() {
        socket.io().reconnection();
    }

    private void addSlideSocketListener() {
        socket.on("sync_v1/ongoing_lectures/set_slide:" + getOngoingId(), objects -> {
            try {
                JSONObject json = (JSONObject) objects[0];
                parseSlide(json);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        socket.io().reconnection();
    }

    private void addQuestionSocketListener(String questionId) {
        socket.on("sync_v1/questions:" + getOngoingId() + ":" + questionId, objects -> {
            try {
                String action = (String) ((JSONObject) objects[0]).get("type");
                if (action.equals(QUESTION_START)) {
                    onQuestionStart.accept(action);
                } else if (action.equals(QUESTION_FINISH)) {
                    onQuestionFinish.accept(action);
                } else {
                    throw new Exception("sync_v1/questions: WRONG TYPE");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        socket.io().reconnection();
    }

    private void addQuestionBlockSocketListener(String questionBlockId) {
        socket.on("sync_v1/blocks:" + getOngoingId() + ":" + questionBlockId, objects -> {
            try {
                String action = (String) ((JSONObject) objects[0]).get("type");
                if (action.equals(QUESTION_BLOCK_START)) {
                    onQuestionBlockStart.accept(action);
                } else if (action.equals(QUESTION_BLOCK_FINISH)) {
                    onQuestionBlockFinish.accept(action);
                } else {
                    throw new Exception("sync_v1/blocks: WRONG TYPE");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private void parseSlide(JSONObject obj) throws Exception {
        String tempState = (String) obj.get("type");
        this.index = (int) obj.get("index");

        if (tempState.equals(SLIDE)) {
            getLecture().setState(tempState);
            JSONObject textSlide = (JSONObject) obj.get("content");
            parseTextSlideBody(textSlide);
        } else if (tempState.equals(QUESTION)) {
            getLecture().setState(tempState);
            JSONObject questionSlide = (JSONObject) obj.get("content");
            Question question = parseQuestionSlideBody(questionSlide);
            onQuestion.accept(question);
            addQuestionSocketListener(question.getId());
        } else if (tempState.equals(QUESTION_BLOCK)) {
            JSONObject questionBlockSlide = (JSONObject) obj.get("content");
            QuestionBlock questionBlock = parseQuestionBlockSlideBody(questionBlockSlide);
            onQuestionBlock.accept(questionBlock);
            addQuestionBlockSocketListener(questionBlock.getId());
        } else {
            System.out.println("wrong signal implemented on set_slide");
        }
    }

    private void parseTextSlideBody(JSONObject obj) throws Exception {
        String content = (String) obj.get("content");
        String name = (String) obj.get("name");
        JSONArray jsonImageArray = (JSONArray) obj.get("images");
        JSONArray jsonImageScaleArray = (JSONArray) obj.get("imagesScale");

        ArrayList<Image> imageArrayList = new ArrayList<>();
        //content: images, imagesScale

        for (int i = 0; i < jsonImageArray.length(); ++i) {
            String path = jsonImageArray.getString(i);
            try {
                Integer scale = jsonImageScaleArray.getInt(i);
                imageArrayList.add(new Image(path, scale));
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("images count doesn't fit corresponding scales count");
            }
        }

        onSimpleSlide.accept(new Slide(name, content, imageArrayList));
    }

    private QuestionBlock parseQuestionBlockSlideBody(JSONObject obj) throws Exception {
        String id = (String) obj.get("_id");
        String name = (String) obj.get("name");
        QuestionBlock questionBlock = new QuestionBlock(name, id);

        JSONArray jsonQuestionsIds = (JSONArray) obj.get("questionsIds");

        for (int i = 0; i < jsonQuestionsIds.length(); ++i) {
            questionBlock.addQuestion(parseQuestionSlideBody(jsonQuestionsIds.getJSONObject(i)));
        }
        return questionBlock;
    }

    private Question parseQuestionSlideBody(JSONObject obj) throws Exception {
        String question = (String) obj.get("question");
        JSONArray jsonAnswers = (JSONArray) obj.get("answers");
        JSONArray jsonCorrectAnswers = (JSONArray) obj.get("correctAnswers");
        String id = (String) obj.get("_id");

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

    private JSONObject parseShortLecture(JSONObject obj) throws JSONException {
        return (JSONObject) obj.get("shortLecture");
    }

    private String parseOngoingId(JSONObject obj) throws JSONException {
        JSONObject shortLectureObj = parseShortLecture(obj);
        if (lecture.getState().equals(LECTURE_START)) {
            lecture.setName((String) shortLectureObj.get("name"));
        }
        return (String) shortLectureObj.get("ongoingId");
    }

    private String parseType(JSONObject obj) throws JSONException {
        return (String) obj.get("type");
    }
}