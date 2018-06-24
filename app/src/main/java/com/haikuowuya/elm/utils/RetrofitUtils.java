package com.haikuowuya.elm.utils;

import com.haikuowuya.elm.api.ApiService;
import com.haikuowuya.elm.http.ParamsInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//https://github.com/sweetying520/RxJavaDemo
public class RetrofitUtils {

    private static final int DEFAULT_TIME_OUT = 10;//超时时间5s
    private static final int DEFAULT_READ_TIME_OUT = 10;//读取时间
    private static final int DEFAULT_WRITE_TIME_OUT = 10;//读取时间
    private Retrofit mRetrofit;

    private RetrofitUtils() {
        //OkHttpClient配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);

        addInterceptor(builder);


        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(ApiService.BASR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 添加各种拦截器
     *
     * @param builder
     */
    private void addInterceptor(OkHttpClient.Builder builder) {
        // 添加日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //日志拦截
        builder.addInterceptor(loggingInterceptor);
        //请求参数拦截
        builder.addInterceptor(new ParamsInterceptor());
    }

    //单例 饿汉模式
    private static class SingletonHolder {
        private static RetrofitUtils retrofitServiceManager = new RetrofitUtils();
    }

    public static RetrofitUtils getInstance() {
        return SingletonHolder.retrofitServiceManager;
    }

    public ApiService getApiService () {
        return mRetrofit.create(ApiService.class);
    }


}
