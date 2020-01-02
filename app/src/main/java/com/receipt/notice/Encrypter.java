package com.receipt.notice;
import java.util.Map;  
public abstract class Encrypter implements IDataTrans{
protected String key;
public Encrypter(String key){
this.key=key;
}
@Override
public abstract Map<String,String> transferMapValue(Map<String, String> params);




}
