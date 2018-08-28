package com.example.administrator.idlereader.news.nba;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSComment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSLightComment;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INbaBBSView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NbaH5Fragment extends BaseRecyclerFragment implements INbaBBSView {
    @BindView(R.id.wb_news)
    WebView mWbNews;
    Unbinder unbinder;
    private static final String TAG = "NbaH5Fragment";
    public String nid, tid;
    public static final String NBA_H5_NID = "NBA_H5_NID";
    public static final String NBA_H5_TID = "NBA_H5_TID";
    private NbaBBSHeaderView mNbaBBSHeaderView;
    private NbaDetailAdapter mNbaDetailAdapter;
    private NewsPresenter mNewsPresenter;

    public static NbaH5Fragment getInstance() {
        NbaH5Fragment fragment = new NbaH5Fragment();
        return fragment;
    }

    @Override
    public void init() {
        nid = getActivity().getIntent().getStringExtra(NBA_H5_NID);
        tid = getActivity().getIntent().getStringExtra(NBA_H5_TID);
        mNewsPresenter = new NewsPresenter(this);
        Map<String, String> parmes = new HashMap<>();
        parmes.put("page","1");
        parmes.put("tid", tid);
        mNewsPresenter.loadNbaBBSComment(parmes);
        mNewsPresenter.loadNbaLightBBSComment(parmes);
        mNbaBBSHeaderView = new NbaBBSHeaderView(getActivity());
        mNbaBBSHeaderView.setData(tid);
        mNbaDetailAdapter.setHeaderView(mNbaBBSHeaderView);
        mRvNews.setAdapter(mNbaDetailAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showCommentData(NbaBBSComment commentData) {
        //mNbaDetailAdapter.setData(commentData.getResult().getList(),true);
    }

    @Override
    public void showLightCommentData(NbaBBSLightComment commentData) {

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
}
