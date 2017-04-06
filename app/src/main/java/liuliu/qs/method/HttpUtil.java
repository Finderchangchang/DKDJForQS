package liuliu.qs.method;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/20.
 */

public class HttpUtil {
    //    public static final String BASE_URL = "http://192.168.1.222/";
//    public static final String BASE_URL = "http://s-352911.gotocdn.com/";
    public static final String BASE_URL = "http://www.dakedaojia.com/";

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;


    public static GitHubAPI load() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        return retrofit.create(GitHubAPI.class);
    }
}
