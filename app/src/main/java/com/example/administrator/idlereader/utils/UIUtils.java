package com.example.administrator.idlereader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.idlereader.DetailActivity;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.news.nba.NbaDetailFragment;
import com.example.administrator.idlereader.news.nba.NbaH5Fragment;
import com.example.administrator.idlereader.news.weibo.WeiBoDetailFragment;
import com.example.administrator.idlereader.news.weibo.WeiBoLoginActivity;
import com.example.administrator.idlereader.news.weibo.WeiBoSpaceActivity;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.administrator.idlereader.utils.RegularUtils.regex_at;
import static com.example.administrator.idlereader.utils.RegularUtils.regex_http;
import static com.example.administrator.idlereader.utils.RegularUtils.regex_sharp;

public class UIUtils {
    public static final String FRAGMENT_CLASS = "FRAGMENT_CLASS";

    public static void startNbaNewsFragment(Context context, String nid) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(FRAGMENT_CLASS, NbaDetailFragment.class.getName());
        intent.putExtra(NbaDetailFragment.NBA_NID, nid);
        context.startActivity(intent);
    }

    public static void startNbaH5Fragment(Context context, String nid,String tid) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(FRAGMENT_CLASS, NbaH5Fragment.class.getName());
        intent.putExtra(NbaH5Fragment.NBA_H5_NID, nid);
        intent.putExtra(NbaH5Fragment.NBA_H5_TID, tid);
        context.startActivity(intent);
    }

    public static void startSpaceFragment(Context context,String uid) {
        Intent intent = new Intent(context, WeiBoSpaceActivity.class);
        intent.putExtra(WeiBoSpaceActivity.WEIBO_SPACE_UID, uid);
        context.startActivity(intent);
    }

    public static void startWeiBoDetailFragment(Context context, String nid) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(FRAGMENT_CLASS, WeiBoDetailFragment.class.getName());
        intent.putExtra(WeiBoDetailFragment.WEIBO_NID, nid);
        context.startActivity(intent);
    }

    public static void startWeiBoLoginActivity(Activity activity){
        Intent intent = new Intent(activity, WeiBoLoginActivity.class);
        activity.startActivity(intent);
    }

    public static void setUpIndicatorWidth(TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(marginLeft);
                    params.setMarginEnd(marginRight);
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //文字转换
    public static SpannableString setTextHighLight(Context context, String content, String nickName,
                                                   boolean isLongText) {
        SpannableString result = new SpannableString(content);
        if (nickName != null) {
            result.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.weiboLight)),
                    0, nickName.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        if (isLongText){
            result.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.weiboLight)),
                    result.length()-2, result.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        if (content.contains("@")) {
            Pattern p = Pattern.compile(regex_at);
            Matcher m = p.matcher(result);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                result.setSpan(
                        (new ForegroundColorSpan(context.getResources().getColor(R.color.weiboLight))),
                        start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }

        if (content.contains("#")) {
            Pattern p = Pattern.compile(regex_sharp);
            Matcher m = p.matcher(result);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                result.setSpan(
                        (new ForegroundColorSpan(context.getResources().getColor(R.color.weiboLight))),
                        start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }

        if (content.contains("http://")) {
            Pattern p = Pattern.compile(regex_http);
            Matcher m = p.matcher(result);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                result.setSpan(
                        (new ForegroundColorSpan(context.getResources().getColor(R.color.weiboLight))),
                        start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return result;
    }

    private static SpannableStringBuilder getUrlTextSpannableString(SpannableString source) {
        SpannableStringBuilder builder = new SpannableStringBuilder(source);
        String prefix = " ";
        builder.replace(0, prefix.length(), prefix);
        builder.append(" 网页链接");
        return builder;
    }
}
