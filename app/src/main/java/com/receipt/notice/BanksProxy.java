package com.receipt.notice;

import android.app.Notification;
import android.text.TextUtils;

import java.util.Map;
import java.util.HashMap;


public class BanksProxy extends NotificationHandle {
    private BankDistinguisher onedistinguisher = new BankDistinguisher();

    public BanksProxy(String pkgType, Notification notification, IDoPost postPush) {
        super(pkgType, notification, postPush);
    }

    private String getBankType() {
        return onedistinguisher.distinguishByMessageContent(content);
    }


    @Override
    public void handleNotification() {
        String banktype = getBankType();
        if (banktype == null) {
            return;
        }

        String type;
        if (TextUtils.isEmpty(banktype)) {
            type = "message-bank";
        } else {
            type = "message-bank-" + banktype;
        }

        Map<String, String> postmap = new HashMap<>(16);
        postmap.put("type", type);
        postmap.put("time", onedistinguisher.extractTime(content, notitime));
        postmap.put("title", "短信银行卡入账");
        postmap.put("phonenum", title);
        postmap.put("money", onedistinguisher.extractMoney(content));
        postmap.put("cardnum", onedistinguisher.extractCardNum(content));
        postmap.put("content", content);

        postpush.doPost(postmap);
        return;
    }
}
