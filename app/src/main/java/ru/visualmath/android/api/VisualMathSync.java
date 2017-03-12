import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import ru.visualmath.android.api.Question;
import ru.visualmath.android.api.Slide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// sync_v1/questions:${activeLectureId}:${questionId} -- какая-то херня start, finish
// `sync_v1/blocks:${activeLectureId}:${blockId}` -- block start, finish

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

    public String getOngoingId() {
        return this.ongoingId;
    }

    public String getState() {
        return this.state;
    }

    private Function<Slide,?> onSimpleSlide;
    private Function<Question, ?> onQuestion;
    // onQuestionBlock
    private Function<String, ?> onLectureStart;;
    private Function<String, ?> onLectureFinish;

    public void setSlideCallback(Function <Slide, ?> nextSlideCallback) {
        this.onSimpleSlide = nextSlideCallback;
    }

    public void setQuestionCallback(Function <Question, ?> nextQuestionCallback) {
        this.onQuestion = nextQuestionCallback;
    }

    public void setLectureFinishCallback(Function <String,?> lectureFinishCallback) {
        this.onLectureFinish = lectureFinishCallback;
    }

    public void setLectureStartCallback(Function <String,?> lectureStartCallback) {
        this.onLectureStart = lectureStartCallback;
    }

    public static void main(String[] args) {
        try {
            VisualMathSync api = new VisualMathSync(new URI("http://sync.visualmath.ru"));
            api.setLectureStartCallback(state -> {
                System.out.println(state);
                return 0;
            });

            api.setLectureFinishCallback(state -> {
                System.out.println(state);
                return 0;
            });

            api.setSlideCallback(slide -> {
                System.out.println(slide);
                return 0;
            });

            api.setQuestionCallback(question -> {
                System.out.println(question);
                return 0;
            });
            api.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public VisualMathSync(URI address) throws URISyntaxException {
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
                    onLectureStart.apply(LECTURE_START);
                    addSlideSocketListener();
                } else if (state.equals(LECTURE_FINISH)) {
                    onLectureFinish.apply(LECTURE_FINISH);
                }
            }
        });
    }

    public void connect() {
        socket.connect();
    }

    private void addSlideSocketListener() {
        socket.on("sync_v1/ongoing_lectures/set_slide:" + getOngoingId() , new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                try {
                    parseSlide((JSONObject) objects[0]);
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        socket.io().reconnection();
    }

    private void addQuestionSocketListener(String questionId) {
        socket.on("sync_v1/questions:" + getOngoingId() + ":" + questionId, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                try {
                    String type = (String)((JSONObject)objects[0]).get("type");
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        socket.io().reconnection();
    }

    private void parseSlide(JSONObject obj) throws JSONException {
        String tempState = (String) obj.get("type");
        if (tempState.equals(SLIDE)) {
            state = tempState;
            JSONObject textSlide = (JSONObject)obj.get("content");
            try {
                parseTextSlideBody(textSlide);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (tempState.equals(QUESTION)) {
            state = tempState;
            JSONObject questionSlide = (JSONObject)obj.get("content");
            try {
                String questionId = parseQuestionSlideBody(questionSlide);
                addQuestionSocketListener(questionId);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (tempState.equals(QUESTION_BLOCK)) {
            System.out.println(QUESTION_BLOCK + "is not implemented yet!");
        } else {
            System.out.println("wrong signal implemented on set_slide");
        }
    }

    private void parseTextSlideBody(JSONObject obj) throws JSONException, Exception {
        String content = (String)obj.get("content");
        String name = (String)obj.get("name");

        onSimpleSlide.apply(new Slide(name, content));
    }

    private String parseQuestionSlideBody(JSONObject obj) throws  JSONException, Exception {
        String question = (String)obj.get("question");
        JSONArray answers = (JSONArray)obj.get("answers");

        ArrayList<String> answersList = new ArrayList<String>();
        for (int i = 0; i < answers.length(); ++i) {
            answersList.add(answers.getString(i));
        }

        onQuestion.apply(new Question(question, answersList));
        return (String)obj.get("_id");
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