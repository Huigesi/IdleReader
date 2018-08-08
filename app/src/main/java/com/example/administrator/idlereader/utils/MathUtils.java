package com.example.administrator.idlereader.utils;

public class MathUtils {

    public static int maximum(int... xs) {
        int m = Integer.MIN_VALUE;
        for (int x : xs)
            m = Math.max(m, x);
        return m;
    }
}
