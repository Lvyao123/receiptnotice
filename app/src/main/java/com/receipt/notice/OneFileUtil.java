/*
 * Copyright (c) 2017
 * All right reserved.
 */

package com.receipt.notice;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Deque;

public class OneFileUtil {
    private File file;

    public OneFileUtil(File file) {
        this.file = file;
    }

    private String clearOnegroup(Deque<String> onegroup) {
        StringBuilder tmp = new StringBuilder();
        while (onegroup.size() > 0) {
            String first = onegroup.pollFirst();
            tmp.append("\n").append(first);
        }
        return tmp.toString();

    }

    public ArrayList mergeByFlagline(String startflagline, String endflagline, ArrayList filelist) {
        if (filelist.size() == 0) {
            return null;
        }
        ArrayList<String> merge = new ArrayList<>();
        Iterator fileIterator = filelist.iterator();
        Deque<String> oneGroup = new LinkedList<>();
        while (fileIterator.hasNext()) {
            String o = (String) fileIterator.next();
            oneGroup.offerLast(o);
            if (oneGroup.peekFirst().contains(startflagline) && oneGroup.peekLast().contains(endflagline)) {
                merge.add(clearOnegroup(oneGroup));

            }

        }
        return merge;


    }

    public ArrayList getFileList() {
        ArrayList<String> arrayList = new ArrayList<>();
        FileReader fr = null;
        try {
            if (!file.exists()) {
                return null;
            }

            fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                //按行处理
                arrayList.add(str);
            }
            if (arrayList.size() == 0) {
                return null;
            } else {
                return arrayList;
            }


        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
