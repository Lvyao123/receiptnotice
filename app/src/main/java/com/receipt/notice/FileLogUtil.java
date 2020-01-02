/*
 * Copyright (c) 2017
 * All right reserved.
 */

package com.receipt.notice;

import android.util.Log;

import com.google.gson.Gson;
import com.tao.admin.loglib.FileUtils;
import com.tao.admin.loglib.TLogApplication;
import com.tao.admin.loglib.IConfig;

import java.io.File;
import java.util.ArrayList;

/**
 * @author youtui
 */
public class FileLogUtil extends FileUtils {
    public static boolean clearLogFile() {
        try {
            File file = new File(TLogApplication.getAPP().getFilesDir(), IConfig.fileName);
            if (file.delete()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public static ArrayList getLogList() {
        File file = new File(TLogApplication.getAPP().getFilesDir(), IConfig.fileName);
        if (!file.exists()) {
            return null;
        }

        OneFileUtil fileutil = new OneFileUtil(file);
        ArrayList filelist = fileutil.getFileList();
        Log.e("filelist", new Gson().toJson(filelist));
        String startFlag = "*********************************";
        String endFlag = "------------------------------------------";
        ArrayList fileMergeList = fileutil.mergeByFlagline(startFlag, endFlag, filelist);
        return fileMergeList;

    }

}
