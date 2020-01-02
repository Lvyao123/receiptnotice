/*
 * Copyright (c) 2017
 * All right reserved.
 */

package com.receipt.notice;

import java.util.Random;

/**
 * @author youtui
 */
public class RandomUtil {
    public static String getRandomTaskNum() {
        Random rand = new Random();
        return String.valueOf(rand.nextInt(9000) + 1000);
    }
}
