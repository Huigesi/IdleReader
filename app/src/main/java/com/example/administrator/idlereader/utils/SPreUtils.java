package com.example.administrator.idlereader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;

public class SPreUtils {
    public static final String NAME_WEIBO_USERINFO = "userinfo";
    public static final String WEIBO_GSID= "WEIBO_GSID";
    public static final String WEIBO_UID= "WEIBO_UID";


    public static void setWeiBoUserInfo(WeiBoUserInfo data,Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(NAME_WEIBO_USERINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(WEIBO_GSID, data.getGsid());
        editor.putString(WEIBO_UID, data.getUid());
        editor.commit();
    }

    public static String getWeiBoUserInfo(String key,Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(NAME_WEIBO_USERINFO, Context.MODE_PRIVATE);
        String result=sharedPreferences.getString(key, "");
        return result;
    }

}
