package com.example.administrator.idlereader.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.idlereader.DetailActivity;
import com.example.administrator.idlereader.news.NbaDetailFragment;

import java.lang.reflect.Field;

public class UIUtils {
    public static final String FRAGMENT_CLASS = "FRAGMENT_CLASS";
    public static void startNbaNewsFragment(Context context,String nid) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(FRAGMENT_CLASS, NbaDetailFragment.class.getName());
        intent.putExtra(NbaDetailFragment.NBA_NID,nid);
        context.startActivity(intent);
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
}
