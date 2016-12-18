package ru.visualmath.android.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.visualmath.android.api.model.LoginParams;
import ru.visualmath.android.api.model.User;

public class VisualMathApi {

    private static VisualMathApi api = new VisualMathApi();
    VisualMathService service;

    private VisualMathApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://visualmath.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(VisualMathService.class);
    }

    public static VisualMathApi getApi() {
        return api;
    }

    public Observable<User> login(String name, String password) {
        return service.login(new LoginParams(name, password));
    }
}
