package com.receipt.notice.api;

import com.receipt.notice.Constants;
import com.receipt.notice.utils.ConfigUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author youtui
 */
public class ApiClient {
    private static Retrofit mRetrofit;
    static {
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl httpUrl = chain.request().url().newBuilder()
                            .addQueryParameter("token", ConfigUtil.getString(Constants.APP_TOKEN))
                            .build();
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .url(httpUrl)
                            .method(request.method(), request.body());
                    return chain.proceed(builder.build());
                })
                .build();
     Retrofit.Builder builder =  new  Retrofit.Builder()
              //.baseUrl("http://callback.thepapayas.com/App/")
             .baseUrl("http://10.0.0.77/App/")
             .client(okHttpClient)
             .addConverterFactory(JacksonConverterFactory.create());
     mRetrofit = builder.build();

    }

    public static <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
