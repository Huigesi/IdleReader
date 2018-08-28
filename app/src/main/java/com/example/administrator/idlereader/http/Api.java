package com.example.administrator.idlereader.http;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Api {
    public static final int NETEASE_NEWS = 0;
    public static final int DOUBAN_MOVIE = 1;
    public static final int TOADAY_NEWS = 2;
    public static final int HUPU_NBA = 3;
    public static final int WEIBO_LIST = 4;
    public static final int HUPU_BBS = 5;

    public static final String NEWS_HOST = "https://c.m.163.com/";
    public static final String MOVIE_HOST = "https://api.douban.com/";
    public static final String TODAY_HOST = "http://is.snssdk.com/api/";
    public static final String HUPU_HOST = "http://games.mobileapi.hupu.com/1/7.2.5/";
    public static final String HUPU_BBS_HOST = "http://bbs.mobileapi.hupu.com";
    public static final String WEIBO_HOST = "http://api.weibo.cn/2/";

    public static final String HEADLINE_ID = "T1348647909107";
    public static final String NBA_ID = "T1348649145984";
    public static final String JOKE_ID = "T1350383429665";
    public static final String GAME_ID = "T1348654151579";
    public static final String HUPU_CLIENT_ID = "866493031799950";

    /*
     * 新浪微博图片链接
     * */
    public static final String IMG_WEIBO_WAP180 = "https://wx3.sinaimg.cn/wap180/";
    public static final String IMG_WEIBO_WAP360 = "https://wx3.sinaimg.cn/wap360/";
    public static final String IMG_WEIBO_WAP720 = "https://wx3.sinaimg.cn/wap720/";
    public static final String IMG_WEIBO_ORIGINAL = "https://wx4.sinaimg.cn/woriginal/";

    public static String getHost(int hostType) {
        switch (hostType) {
            case NETEASE_NEWS:
                return NEWS_HOST;
            case DOUBAN_MOVIE:
                return MOVIE_HOST;
            case TOADAY_NEWS:
                return TODAY_HOST;
            case HUPU_NBA:
                return HUPU_HOST;
            case WEIBO_LIST:
                return WEIBO_HOST;
            case HUPU_BBS:
                return HUPU_BBS_HOST;
        }
        return "";
    }
}
