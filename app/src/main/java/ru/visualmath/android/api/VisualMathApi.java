package ru.visualmath.android.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.LoginParams;
import ru.visualmath.android.api.model.User;

public class VisualMathApi {

    private static VisualMathApi api = new VisualMathApi();
    VisualMathService service;
    String cookie = "";

    private VisualMathApi() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(chain
                        .request()
                        .newBuilder()
                        .addHeader("Cookie", cookie)
                        .build()))
                .addInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());

                    cookie = response.header("Set-Cookie", "");

                    return response;
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://visualmath.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(VisualMathService.class);
    }

    public static VisualMathApi getApi() {
        return api;
    }

    public Observable<User> login(String name, String password) {
        return service.login(new LoginParams(name, password));
    }

    public Observable<List<Lecture>> lecturesList() {
        return service.lecturesList();
    }
}
