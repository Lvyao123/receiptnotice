package com.receipt.notice;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.Cipher;

public class DES extends Encrypter {
    public DES(String key) {
        super(key);
    }

    @Override
    public Map<String, String> transferMapValue(Map<String, String> params) {
        Map<String, String> postmap = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            String paramkey = (String) ((Map.Entry) stringStringEntry).getKey();
            String paramvalue = (String) ((Map.Entry) stringStringEntry).getValue();
            String desStr = DESUtilWithIV.des(paramvalue, key, Cipher.ENCRYPT_MODE);
            postmap.put(paramkey, desStr);

        }
        postmap.put("encrypt", "1");
        LogUtil.debugLogWithJava("调试，开始加密字符串");
        LogUtil.debugLogWithJava("加密后的map");
        LogUtil.debugLogWithJava(postmap.toString());
        return postmap;
    }
}
