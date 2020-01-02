package com.receipt.notice;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class PostMapFilter {
    private Map<String, String> unmodifiedmap;
    private PreferenceUtil preference;
    private String posturl;

    public PostMapFilter(PreferenceUtil preference, Map<String, String> unModifiedMap, String postUrl) {
        this.preference = preference;
        this.unmodifiedmap = unModifiedMap;
        this.posturl = postUrl;
    }

    public String getDeviceid() {
        String deviceid = preference.getDeviceid();
        if (TextUtils.isEmpty(deviceid)) {
            deviceid = DeviceInfoUtil.getUniquePsuedoID();
        } else if (preference.isAppendDeviceiduuid()) {
            deviceid = deviceid + '-' + DeviceInfoUtil.getUniquePsuedoID();
        } else {
            deviceid = deviceid;
        }
        return deviceid;
    }


    public Map getPostMap() {
        Map<String, String> postmap = new HashMap<>();
        postmap.putAll(getLogMap());
        if (preference.isEncrypt()) {
            String encrypt_type = preference.getEncryptMethod();
            if (encrypt_type != null) {
                String key = preference.getPasswd();
                EncryptFactory encryptfactory = new EncryptFactory(key);
                LogUtil.debugLog("加密方法" + encrypt_type);
                LogUtil.debugLog("加密秘钥" + key);
                Encrypter encrypter = encryptfactory.getEncrypter(encrypt_type);
                if (encrypter != null && key != null) {
                    postmap = encrypter.transferMapValue(postmap);
                    postmap.put("url", this.posturl);
                    if (preference.isSkipEncryptDeviceid()) {
                        postmap.put("deviceid", getDeviceid());
                    }
                }

            }
        } else {
            postmap.put("encrypt", "0");
        }
        return postmap;
    }

    public Map getLogMap() {
        Map<String, String> recordmap = new HashMap<>();
        recordmap.putAll(this.unmodifiedmap);
        recordmap.put("url", this.posturl);
        recordmap.put("deviceid", getDeviceid());
        if (!TextUtils.isEmpty(preference.getCustomOption())) {
            Map custompostoption = ExternalInfoUtil.getCustomPostOption(preference.getCustomOption());
            if (custompostoption != null) {
                recordmap.putAll(custompostoption);
            }
        }
        return recordmap;

    }


}
