package com.example.administrator.idlereader.http;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Api {
    public static final int NETEASE_NEWS = 0;
    public static final int DOUBAN_MOVIE = 1;
    public static final int TOADAY_NEWS = 2;
    public static final String NEWS_HOST = "https://c.m.163.com/";
    public static final String MOVIE_HOST = "https://api.douban.com/";
    public static final String TODAY_HOST = "http://is.snssdk.com/api/";

    public static final String HEADLINE_ID = "T1348647909107";
    public static final String NBA_ID = "T1348649145984";
    public static final String JOKE_ID = "T1350383429665";
    public static final String GAME_ID = "T1348654151579";
    public static String getHost(int hostType) {
        switch (hostType) {
            case NETEASE_NEWS:
                return NEWS_HOST;
            case DOUBAN_MOVIE:
                return MOVIE_HOST;
            case TOADAY_NEWS:
                return TODAY_HOST;
        }
        return "";
    }
}
