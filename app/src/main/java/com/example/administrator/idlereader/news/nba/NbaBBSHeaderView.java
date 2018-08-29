package com.example.administrator.idlereader.news.nba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.administrator.idlereader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NbaBBSHeaderView extends LinearLayout {
    @BindView(R.id.wb_news)
    WebView mWbNews;
    private Unbinder mUnbinder;
    private String mTid;


    public NbaBBSHeaderView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_hupu_h5, this, true);
        mUnbinder = ButterKnife.bind(this);
    }

    public void setData(String data) {
        mTid = data;
        refreshUI();
    }

    private void refreshUI() {
        mWbNews.getSettings().setJavaScriptEnabled(true);
        mWbNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWbNews.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        String url = "http://bbs.mobileapi.hupu.com/1/7.2.8/threads/getThreadDetailInfoH5?tid=";
        mWbNews.loadUrl(url + mTid);
        mWbNews.setWebViewClient(new WebViewClient());
        mWbNews.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mWbNews.performClick();  //模拟父控件的点击
                }
                return false;
            }
        });
    }
}
