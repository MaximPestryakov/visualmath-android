package ru.visualmath.android.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.api.model.User;
import ru.visualmath.android.api.model.UserInfo;

interface VisualMathService {

    String URL = "http://visualmath.ru/api/";

    @POST("login")
    Single<User> login(@Body Map<String, String> data);

    @GET("logout")
    Completable logout();

    @POST("users/create")
    Single<User> createUser(@Body Map<String, String> data);

    @GET("lectures/list")
    Single<List<Lecture>> lecturesList();

    @POST("questionBlock/load")
    Single<QuestionBlock> loadQuestionBlock(@Body Map<String, String> data);

    @GET("sync_v1/lectures/list")
    Single<List<SyncLecture>> syncLectureList();

    @POST("sync_v1/ongoing_lectures/load_lecture")
    Single<ResponseBody> loadSyncLecture(@Body Map<String, String> data);

    @POST("sync_v1/ongoing_lectures/load_slide")
    Single<ResponseBody> loadSyncSlide(@Body Map<String, String> data);

    @POST("sync_v1/questions/answer")
    Single<ResponseBody> answerQuestion(@Body Map<String, Object> data);

    @POST("sync_v1/blocks/answer")
    Single<ResponseBody> answerBlock(@Body Map<String, Object> data);

    @POST("sync_v1/ongoing_lectures/userinfo")
    Single<UserInfo> userInfo(@Body Map<String, Object> data);
}
