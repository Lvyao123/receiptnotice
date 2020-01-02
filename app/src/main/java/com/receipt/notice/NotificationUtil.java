/*
 * Copyright (c) 2017
 * All right reserved.
 */

package com.receipt.notice;

import android.app.Notification;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @author youtui
 */
public class NotificationUtil {
    private static String getNotitime(Notification notification) {

        long when = notification.when;
        Date date = new Date(when);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String notitime = format.format(date);
        return notitime;

    }

    private static String getNotiTitle(Bundle extras) {
        String title = null;
        // 获取通知标题
        title = extras.getString(Notification.EXTRA_TITLE, "");
        return title;
    }

    private static String getNotiContent(Bundle extras) {
        String content;
        // 获取通知内容
        content = extras.getString(Notification.EXTRA_TEXT, "");
        return content;
    }

    public static void printNotify(Notification notification) {
        LogUtil.debugLog(getNotitime(notification));
        LogUtil.debugLog(getNotiTitle(notification.extras));
        LogUtil.debugLog(getNotiContent(notification.extras));
    }

}
