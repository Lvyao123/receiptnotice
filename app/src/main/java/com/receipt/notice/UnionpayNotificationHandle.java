package com.receipt.notice;

import android.app.Notification;

import java.util.Map;
import java.util.HashMap;


/**
 * @author youtui
 */
public class UnionpayNotificationHandle extends NotificationHandle {
    public UnionpayNotificationHandle(String pkgtype, Notification notification, IDoPost postpush) {
        super(pkgtype, notification, postpush);
    }

    @Override
    public void handleNotification() {
        if (title.contains("消息推送") && (content.contains("银行") || content.contains("尾号"))) {
            Map<String, String> postmap = new HashMap<>();
            postmap.put("type", "unionpay");
            postmap.put("time", notitime);
            postmap.put("title", title);
            postmap.put("money", extractMoney(content));
            postmap.put("content", content);
            postpush.doPost(postmap);
            return;
        }

    }

}
