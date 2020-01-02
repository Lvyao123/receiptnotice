package com.receipt.notice;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.view.LayoutInflater;
import android.widget.TextView;
import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author youtui
 */
public class LogListAdapter extends Adapter<LogListAdapter.RecyclerHolder> {
    private final LayoutInflater mLayoutInflater;
    private ArrayList logList;

    LogListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setLogList(ArrayList logList) {
        this.logList = logList;
    }

    @Override
    public int getItemCount() {
        return logList == null ? 0 : logList.size();
    }


    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        String oneRecord = (String) logList.get(position);
        if (holder.recordText == null) {
            return;
        }
        if (oneRecord != null) {
            if (oneRecord.contains("url")) {
                String content = oneRecord.substring(oneRecord.indexOf("{") + 1, oneRecord.indexOf("}")).trim();
                Log.e("oneRecord", new Gson().toJson(content));
                Log.e("oneRecord", content);
                String[] array = content.split(",");
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : array) {
                    if (!s.contains("deviceid") && !s.contains("url") && !s.contains("content")) {
                        stringBuilder.append(s).append("\n");
                    }

                }
                holder.recordText.setText(stringBuilder);
            } else {
                holder.recordText.setText(oneRecord);
            }


        } else {
            LogUtil.debugLogWithDeveloper("获取到的loglist 的item text为空");
        }

    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerHolder(mLayoutInflater.inflate(R.layout.item_log_text, parent, false));

    }


    class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView recordText;

        RecyclerHolder(View view) {
            super(view);
            recordText = view.findViewById(R.id.text_view);
        }

    }

}
