package com.receipt.notice;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.receipt.notice.login.LoginActivity;
import com.receipt.notice.utils.ConfigUtil;

/**
 * @author youtui
 */
public class HelloFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_hello, container, false);

        initView(mRootView);
        return mRootView;
    }

    private void initView(View view) {

        AppCompatTextView mBtnSwitch = view.findViewById(R.id.btn_switch);
        TextPaint textPaint = mBtnSwitch.getPaint();
        textPaint.setColor(0xff333333);
        textPaint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textPaint.setAntiAlias(true);
        mBtnSwitch.setOnClickListener(v -> {
            if (!isAdded() || getActivity() == null) {
                return;
            }
            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                    .cancelable(true)
                    .title("提示：")
                    .content("切换密钥,之前绑定的密钥将失效")
                    .contentColor(0xff333333)
                    .contentLineSpacing(2.0f)
                    .contentGravity(GravityEnum.CENTER)
                    .dividerColor(0xffe6e6e6)
                    .neutralText("取消")
                    .onNeutral((dialog, which) -> {
                        dialog.dismiss();
                    }).positiveText("确定")
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        confirm();
                    });
            MaterialDialog dialog = builder.build();
            dialog.show();

        });
    }

    private void confirm() {
        if (isAdded() && getActivity() != null) {
            FileLogUtil.clearLogFile();
            ConfigUtil.saveString(Constants.APP_TOKEN, "");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
