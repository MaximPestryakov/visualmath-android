package ru.visualmath.android.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.User;

public interface VisualMathService {
    @POST("login")
    Observable<User> login(@Body Map<String, String> data);

    @GET("lectures/list")
    Observable<List<Lecture>> lecturesList();

    @POST("questionBlock/load")
    Observable<QuestionBlock> loadQuestionBlock(@Body Map<String, String> data);
}
