package com.receipt.notice.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.receipt.notice.BuildConfig;
import com.receipt.notice.Constants;
import com.receipt.notice.MainActivity;
import com.receipt.notice.R;
import com.receipt.notice.api.ApiClient;
import com.receipt.notice.api.LoginApi;
import com.receipt.notice.api.MessageTo;
import com.receipt.notice.base.BaseActivity;
import com.receipt.notice.module.TokenTo;
import com.receipt.notice.utils.ConfigUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author youtui
 */
public class LoginActivity extends BaseActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.edit_account)
    TextInputEditText mEditAccount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        String token = ConfigUtil.getString(Constants.APP_TOKEN);
        if (!TextUtils.isEmpty(token)) {
          Intent intent = new Intent(this, MainActivity.class);
          startActivity(intent);
          finish();
        }
    }

    @OnClick(R.id.btn_login)
    public void login(){

        if (TextUtils.isEmpty(mEditAccount.getText())) {
            Toast.makeText(this, "请输入App密钥", Toast.LENGTH_SHORT).show();
            return;
        }


        LoginApi api = ApiClient.create(LoginApi.class);
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("登录中,请稍等...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        api.login(mEditAccount.getText().toString()).enqueue(new Callback<MessageTo<TokenTo>>() {
            @Override
            public void onResponse(@NotNull Call<MessageTo<TokenTo>> call, @NotNull Response<MessageTo<TokenTo>> response) {
                   dialog.dismiss();
                 Log.e("login", new Gson().toJson(response.body()));
                if (response.body() != null && response.body().getCode() == 1) {
                    TokenTo tokenTo = response.body().getData();
                    ConfigUtil.saveString(Constants.APP_TOKEN, tokenTo.getToken());
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, new Gson().toJson(response.body()));
                    }
                    Intent intent = new Intent(getThisContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getThisContext(), response.body()!=null? response.body().getMessage():"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageTo<TokenTo>> call, @NotNull Throwable t) {
             dialog.dismiss();
            }
        });


    }

    @Override
    public Context getThisContext() {
        return LoginActivity.this;
    }
}
