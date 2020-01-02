package com.receipt.notice;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.Menu;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationManagerCompat;

import android.view.View;
import android.widget.Button;
import android.widget.AutoCompleteTextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
import android.widget.ArrayAdapter;

import androidx.viewpager2.widget.ViewPager2;

import com.github.pedrovgs.lynx.LynxConfig;
import com.github.pedrovgs.lynx.LynxActivity;

/**
 * @author youtui
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Toolbar myToolbar;
    private FloatingActionButton mBtnShowLog;
    private AutoCompleteTextView postUrl;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        postUrlSuggestion();

    }

    private void initView() {

        sp = getSharedPreferences("url", Context.MODE_PRIVATE);
        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("欢迎使用");
        myToolbar.setBackgroundColor(0xffffffff);
        setSupportActionBar(myToolbar);
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        HomeFragmentsAdapter viewpageadapter = new HomeFragmentsAdapter(this);
        viewPager.setAdapter(viewpageadapter);
        Button mBtnSetPostUrl = findViewById(R.id.btnsetposturl);
        mBtnSetPostUrl.setOnClickListener(this);
        mBtnShowLog = findViewById(R.id.floatingshowlog);
        mBtnShowLog.setOnClickListener(this);
        postUrl = findViewById(R.id.posturl);
        if (!TextUtils.isEmpty(getPostUrl())) {
            postUrl.setHint(getPostUrl());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean isAuthor = isNotificationServiceEnable();
        if (!isAuthor) {
            //直接跳转通知授权界面
            //android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS是API 22才加入到Settings里，这里直接写死
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 是否已授权
     *
     * @return
     */
    private boolean isNotificationServiceEnable() {
        return NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsetposturl:
                postUrl.setHint(null);
                setPostUrl();
                break;
            case R.id.floatingshowlog:
                showLog();
                break;
            default:
                break;
        }
    }

    private void setPostUrl() {
        SharedPreferences.Editor edit = sp.edit();
        //通过editor对象写入数据
        edit.putString("postUrl", postUrl.getText().toString());
        //提交数据存入到xml文件中
        edit.apply();
//        Toast.makeText(getApplicationContext(), "已经设置posturl为：" + postUrl.getText().toString(),
//                Toast.LENGTH_SHORT).show();
    }

    private String getPostUrl() {
        String postUrlPath;
        postUrlPath = sp.getString("postUrl", "");
        return postUrlPath;
    }

    private void postUrlSuggestion() {
        String[] str = new String[2];
        str[0] = "";
        str[1] = getPostUrl();
        postUrl.setThreshold(0);
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, str);
        postUrl.setAdapter(adapter);
        postUrl.setOnFocusChangeListener((v, hasFocus) -> {
            AutoCompleteTextView view = (AutoCompleteTextView) v;
            if (hasFocus) {
                view.showDropDown();
            }
        });
        //  Toast.makeText(getApplicationContext(), str[0], Toast.LENGTH_SHORT).show();
    }


    private void showLog() {
        //startActivity(new Intent(this, LogActivity.class));
        openLynxActivity();
    }

    private void openLynxActivity() {
        LynxConfig lynxConfig = new LynxConfig();
        lynxConfig.setMaxNumberOfTracesToShow(4000)
                .setFilter("NLService");

        Intent lynxActivityIntent = LynxActivity.getIntent(this, lynxConfig);
        startActivity(lynxActivityIntent);
    }

    private void openSettingActivity() {
        Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
        // startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                openSettingActivity();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
