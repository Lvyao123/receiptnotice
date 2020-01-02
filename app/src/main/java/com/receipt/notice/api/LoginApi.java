package com.receipt.notice.api;

import com.receipt.notice.module.TokenTo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author youtui
 */
public interface LoginApi {
    /**
     * 登录
     * @param appKey
     * @return
     */
    @FormUrlEncoded
    @POST("login.php")
    Call<MessageTo<TokenTo>> login(@Field("app_key") String appKey);


}
