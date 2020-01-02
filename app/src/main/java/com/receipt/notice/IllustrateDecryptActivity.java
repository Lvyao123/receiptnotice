package com.receipt.notice;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

/**
 * @author youtui
 */
public class IllustrateDecryptActivity extends AppCompatActivity {
    private TextView textMethod;
    private TextView textPasswd;
    private TextView text_iv;
    private PreferenceUtil preference;

    private void initView() {
        textMethod = findViewById(R.id.info_text_method);
        textPasswd = findViewById(R.id.info_text_passwd);
        text_iv = findViewById(R.id.info_text_iv);
        preference = new PreferenceUtil(getBaseContext());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illustratedecrypt);
        initView();
        setText();
    }

    private void setText() {
        String encrypt_type = preference.getEncryptMethod();
        if (encrypt_type == null) {
            textMethod.setText("您没有设置加密方法");
            return;
        }
        if ("des".equals(encrypt_type)) {
            String method = "DES/CBC/PKCS5Padding";
            textMethod.setText("解密的方法为:" + method);
            String key = preference.getPasswd();
            if (key != null) {
                textPasswd.setText("解密秘钥为:" + key + "(des秘钥必须为8位,如果你设置的不是8位，请修改)");
                text_iv.setText("解密的初始化向量为:" + key);
            }
        }

    }
}
