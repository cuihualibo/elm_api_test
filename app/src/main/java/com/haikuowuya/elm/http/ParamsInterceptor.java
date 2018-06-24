package com.haikuowuya.elm.http;

import com.haikuowuya.elm.utils.SoutUtils;

import java.io.IOException;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求公共参数插入器
 * <p>
 *
 */
public class ParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {

            HttpUrl httpUrl = request.url();
            Set<String> names = httpUrl.queryParameterNames();
            for(String name :names)
            {
                SoutUtils.out("name = " + name);
                SoutUtils.out("value = "+ httpUrl.queryParameterValues(name).get(0));
            }
            httpUrl.newBuilder()
//                    .addQueryParameter("version", "xxx")
//                    .addQueryParameter("device", "Android")
//                    .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();
            request = request.newBuilder().url(httpUrl).build();
        } else if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();

                for (int i = 0; i < formBody.size(); i++) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }

                formBody = bodyBuilder
//                        .addEncoded("version", "xxx")
//                        .addEncoded("device", "Android")
//                        .addEncoded("timestamp", String.valueOf(System.currentTimeMillis()))
                        .build();

                request = request.newBuilder().post(formBody).build();
            }
        }

        return chain.proceed(request);
    }
}