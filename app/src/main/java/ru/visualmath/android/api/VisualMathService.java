package ru.visualmath.android.api;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.visualmath.android.api.model.LoginParams;
import ru.visualmath.android.api.model.User;

public interface VisualMathService {
    @POST("login")
    Observable<User> login(@Body LoginParams user);
}
