package com.receipt.notice;

public class EncryptFactory {
    private String key;

    public EncryptFactory(String key) {
        this.key = key;
    }

    public Encrypter getEncrypter(String encrypt_type) {
        if ("des".equals(encrypt_type)) {
            return new DES(key);
        }
        if ("aes".equals(encrypt_type)) {
            return new AES(key);
        }
        LogUtil.debugLog("没有匹配到合适的Encrypter");
        return null;


    }
}
