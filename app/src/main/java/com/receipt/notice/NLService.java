package com.receipt.notice;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.app.Notification;
import android.service.notification.StatusBarNotification;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.google.gson.Gson;
import com.receipt.notice.api.ApiClient;
import com.receipt.notice.api.MessageTo;
import com.receipt.notice.api.PostApi;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NLService extends NotificationListenerService implements AsyncResponse, IDoPost, ActionStatusBarNotification {
    private String TAG = "NLService";
    private String posturl = null;
    private Context context = null;

    private String getPostUrl() {
        SharedPreferences sp = getSharedPreferences("url", 0);
        this.posturl = sp.getString("posturl", "");
        if (posturl == null) {
            return null;
        } else {
            return posturl;
        }
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //        super.onNotificationPosted(sbn);
        //这里只是获取了包名和通知提示信息，其他数据可根据需求取，注意空指针就行
         Log.e("recordMap",  new Gson().toJson(sbn));
//        if (TextUtils.isEmpty(getPostUrl())) {
//            return;
//        }

        Notification notification = sbn.getNotification();
        String pkg = sbn.getPackageName();
        if (notification == null) {
            return;
        }

        Bundle extras = notification.extras;
        if (extras == null) {
            return;
        }

        //接受推送处理
        NotificationHandle noticeHandle = new NotificationHandleFactory().getNotificationHandle(pkg, notification, this);
        if (noticeHandle != null) {
            noticeHandle.setStatusBarNotification(sbn);
            noticeHandle.setActionStatusbar(this);
            noticeHandle.printNotify();
            noticeHandle.handleNotification();
            noticeHandle.removeNotification();
            return;
        }
        LogUtil.debugLog("-----------------");
        LogUtil.debugLog("接受到通知消息");
        LogUtil.debugLog("这是检测之外的其它通知");
        LogUtil.debugLog("包名是" + pkg);
        NotificationUtil.printNotify(notification);
        //printNotify(getNotitime(notification),getNotiTitle(extras),getNotiContent(extras));
        LogUtil.debugLog("**********************");


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT > 19) {
            super.onNotificationRemoved(sbn);
        }
    }

    @Override
    public void removeNotification(StatusBarNotification sbn) {
        PreferenceUtil preference = new PreferenceUtil(getBaseContext());
        if (preference.isRemoveNotification()) {
            if (Build.VERSION.SDK_INT >= 21) {
                cancelNotification(sbn.getKey());
            } else {
                cancelNotification(sbn.getPackageName(), sbn.getTag(), sbn.getId());
            }
            sendToast("receiptnotice移除了包名为" + sbn.getPackageName() + "的通知");
        }
    }

    private void sendBroadcast(String msg) {
        Intent intent = new Intent(getPackageName());
        intent.putExtra("text", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private String getNotitime(Notification notification) {

        long when = notification.when;
        Date date = new Date(when);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String noticeTime = format.format(date);
        return noticeTime;

    }

    private String getNotiTitle(Bundle extras) {
        String title = null;
        // 获取通知标题
        title = extras.getString(Notification.EXTRA_TITLE, "");
        return title;
    }

    private String getNotiContent(Bundle extras) {
        String content = null;
        // 获取通知内容
        content = extras.getString(Notification.EXTRA_TEXT, "");
        return content;
    }

    private void printNotify(String notitime, String title, String content) {
        Log.d(TAG, notitime);
        Log.d(TAG, title);
        Log.d(TAG, content);
    }


    @Override
    public void doPost(Map<String, String> params) {
//        if (this.posturl == null | params == null) {
//            return;
//        }
        LogUtil.debugLog("开始准备进行post");
        if (params.get("repeatnum") != null) {
            doPostTask(params, null);
            return;
        }

        PreferenceUtil preference = new PreferenceUtil(getBaseContext());
        PostMapFilter mapFilter = new PostMapFilter(preference, params, this.posturl);
        Map<String, String> recordMap = mapFilter.getLogMap();
        Map<String, String> postMap = mapFilter.getPostMap();

        doPostTask(postMap, recordMap);


    }

    private void doPostTask(Map<String, String> postMap, Map<String, String> recordMap) {
        Log.e("recordMap", "postMap"+ new Gson().toJson(postMap));
        Log.e("recordMap", "recordMap"+ new Gson().toJson(postMap));

        PostApi api = ApiClient.create(PostApi.class);
        api.post(  postMap.toString()).enqueue(new Callback<MessageTo<Void>>() {
            @Override
            public void onResponse(@NotNull Call<MessageTo<Void>> call, @NotNull Response<MessageTo<Void>> response) {
                Log.e("recordMap", response.body().getMessage());
            }

            @Override
            public void onFailure(@NotNull Call<MessageTo<Void>> call, @NotNull Throwable t) {
                Log.e("recordMap",  new Gson().toJson(t));
            }
        });


        PostTask mTask = new PostTask();
        String taskNum = RandomUtil.getRandomTaskNum();
        mTask.setRandomTaskNum(taskNum);
        mTask.setOnAsyncResponse(this);
        if (recordMap != null) {
            LogUtil.postRecordLog(taskNum, recordMap.toString());
        } else {
            LogUtil.postRecordLog(taskNum, postMap.toString());
        }

        mTask.execute(postMap);


    }


    @Override
    public void onDataReceivedSuccess(String[] returnstr) {
        Log.d(TAG, "Post Receive-returned post string");
        Log.d(TAG, returnstr[2]);
        LogUtil.postResultLog(returnstr[0], returnstr[1], returnstr[2]);


    }

    @Override
    public void onDataReceivedFailed(String[] returnstr, Map<String, String> postedMap) {
        //Auto-generated method stub
        Log.d(TAG, "Post Receive-post error");
        LogUtil.postResultLog(returnstr[0], returnstr[1], returnstr[2]);
        PreferenceUtil preference = new PreferenceUtil(getBaseContext());
        if (preference.isPostRepeat()) {
            String repeatLimit = preference.getPostRepeatNum();
            int limitNum = Integer.parseInt(repeatLimit);
            String repeatNumStr = postedMap.get("repeatnum");
            int repeatNum = Integer.parseInt(repeatNumStr);
            if (repeatNum <= limitNum) {
                doPost(postedMap);
            }
        }

    }
}
