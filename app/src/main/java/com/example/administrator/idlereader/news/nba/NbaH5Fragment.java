package com.example.administrator.idlereader.news.nba;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INbaDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NbaH5Fragment extends Fragment{
    @BindView(R.id.wb_news)
    WebView mWbNews;
    Unbinder unbinder;
    public String nid, tid;
    public static final String NBA_H5_NID = "NBA_H5_NID";
    public static final String NBA_H5_TID = "NBA_H5_TID";

    public static NbaH5Fragment getInstance() {
        NbaH5Fragment fragment = new NbaH5Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hupu_h5, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nid = getActivity().getIntent().getStringExtra(NBA_H5_NID);
        tid = getActivity().getIntent().getStringExtra(NBA_H5_TID);
        mWbNews.getSettings().setJavaScriptEnabled(true);
        mWbNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWbNews.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWbNews.canGoBack();
        mWbNews.canGoForward();
        String url = "http://bbs.mobileapi.hupu.com/1/7.2.5/threads/getThreadDetailInfoH5?tid=";
        mWbNews.loadUrl(url+tid);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
