package com.receipt.notice;

import android.app.Notification;
import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestCashbarNotificationHandle{
        CashbarNotificationHandle cashbar=null;
        protected void setUp() throws Exception {
                IDoPost dopost=new IDoPost(){
                        @Override
                        public  void doPost(Map<String, String> params){
                                if(params!=null)
                                        System.out.println(params.toString());
                        }
                };
                Notification mocknoti=mock(Notification.class);
                Bundle mockextras=mock(Bundle.class);
                when(mockextras.getString(Notification.EXTRA_TITLE, "")).thenReturn("收钱吧");
                when(mockextras.getString(Notification.EXTRA_TEXT, "")).thenReturn("成功收款0.12元,来自支付宝");
                when(mocknoti.extras).thenReturn(mockextras);
                long whenval = 1346524199000l;
                when(mocknoti.when).thenReturn(whenval);
                cashbar=new CashbarNotificationHandle ("package name",mocknoti,dopost);

        }

        @Test
        public void testGetCashbarType() throws Exception{
                setUp();
                cashbar.handleNotification();

        }




}
