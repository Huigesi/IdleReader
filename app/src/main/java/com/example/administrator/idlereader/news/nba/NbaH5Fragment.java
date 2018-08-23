package com.example.administrator.idlereader.news.nba;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

public class NbaH5Fragment extends Fragment implements INbaDetailView {
    @BindView(R.id.wb_news)
    WebView mWbNews;
    Unbinder unbinder;
    public String nid, loadUrl;
    private NewsPresenter mNewsPresenter;
    public static final String NBA_H5_NID = "NBA_H5_NID";

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
        mNewsPresenter = new NewsPresenter(this);
        nid = getActivity().getIntent().getStringExtra(NBA_H5_NID);
        mNewsPresenter.loadNbaDetail(nid);
    }

    @Override
    public void showData(NbaDetailNews data) {
        mWbNews.getSettings().setJavaScriptEnabled(true);
        mWbNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWbNews.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWbNews.canGoBack();
        mWbNews.canGoForward();
        mWbNews.loadUrl(data.getResult().getUrl());
        mWbNews.setWebViewClient(new WebViewClient());
    }

    @Override
    public void showCommentData(NbaNewsComment commentData) {

    }

    @Override
    public void showMoreCommentData(NbaNewsComment commentData) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
