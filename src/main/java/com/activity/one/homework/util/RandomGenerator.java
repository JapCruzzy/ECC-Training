package com.activity.one.homework.util;

import java.util.Random;

public class RandomGenerator {
    public static String getRandom(Integer size) {

        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "!@#$%^&*_-+";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            char ranChar = ALPHA_NUMERIC_STRING.charAt(rnd.nextInt(ALPHA_NUMERIC_STRING.length()));
            sb.append(ranChar);
        }
        return sb.toString();
    }
}
