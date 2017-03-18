package ru.visualmath.android.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.NewUser;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.api.model.User;

interface VisualMathService {
    @POST("login")
    Observable<User> login(@Body Map<String, String> data);

    @POST("users/create")
    Observable<User> createUser(@Body NewUser newUser);

    @GET("lectures/list")
    Observable<List<Lecture>> lecturesList();

    @POST("questionBlock/load")
    Observable<QuestionBlock> loadQuestionBlock(@Body Map<String, String> data);

    @GET("sync_v1/lectures/list")
    Observable<List<SyncLecture>> syncLectureList();

    @POST("sync_v1/ongoing_lectures/load_slide")
    Observable<ResponseBody> loadSyncSlide(@Body ActiveLectureId activeLectureId);
}

class ActiveLectureId {
    private String activeLectureId;

    ActiveLectureId(String activeLectureId) {
        this.activeLectureId = activeLectureId;
    }
}