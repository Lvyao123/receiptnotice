package com.receipt.notice;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class AES extends Encrypter{
        public AES(String key){
                super(key);
        }
        @Override
        public Map<String,String> transferMapValue(Map<String, String> params){
                Map<String,String> postmap= new HashMap<>(16);
                for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                        String paramkey = (String) ((Map.Entry) stringStringEntry).getKey();
                        String paramvalue = (String) ((Map.Entry) stringStringEntry).getValue();
                        //String aesStr = AESUtil.aes(paramvalue, key, Cipher.ENCRYPT_MODE);
                        String aesStr = null;
                        postmap.put(paramkey, aesStr);

                }
                postmap.put("encrypt","1");
                return postmap;




        }












}
