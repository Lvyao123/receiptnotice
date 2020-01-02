package com.receipt.notice.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostApi {
    /**
     *
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("pay_notify.php")
    Call<MessageTo<Void>> post(@Field("content") String content);

}
